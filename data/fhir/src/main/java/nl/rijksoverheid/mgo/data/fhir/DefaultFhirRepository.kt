package nl.rijksoverheid.mgo.data.fhir

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.mapNotNull
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import nl.rijksoverheid.mgo.framework.storage.bytearray.MgoByteArrayStorage
import okhttp3.OkHttpClient
import okhttp3.Request
import timber.log.Timber
import java.io.File
import java.io.IOException
import java.util.Base64
import javax.inject.Inject
import javax.inject.Named
import javax.inject.Singleton

@Singleton
class DefaultFhirRepository
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
    private val okHttpClient: OkHttpClient,
    @Named("encryptedMgoByteArrayStorage") private val mgoByteArrayStorage: MgoByteArrayStorage,
  ) : FhirRepository {
    private val json = Json.Default
    private val cachedFhirResponses = MutableStateFlow<List<FhirResponse>>(listOf())

    override fun observe(
      organizationId: String,
      dataServiceId: String,
      endpointId: String,
    ): Flow<FhirResponse> =
      cachedFhirResponses
        .mapNotNull { responses ->
          responses.firstOrNull { response ->
            response.request.organizationId == organizationId && response.request.dataServiceId == dataServiceId &&
              response.request.endpointId == endpointId
          }
        }.distinctUntilChanged()

    override fun observe(): Flow<List<FhirResponse>> = cachedFhirResponses

    override suspend fun fetch(
      request: FhirRequest,
      forceRefresh: Boolean,
    ) {
      val cacheKey = "${request.organizationId}/${request.dataServiceId}/${request.endpointId}.json"
      val cachedResponseBytes = mgoByteArrayStorage.get(name = cacheKey)
      if (cachedResponseBytes != null && !forceRefresh) {
        // Update the cached response with success state
        val fhirResponse =
          FhirResponse.Success(
            request = request,
            cacheKey = cacheKey,
            isEmpty = isBundleEmpty(cachedResponseBytes.toString(Charsets.UTF_8)),
          )
        updateCachedFhirResponse(fhirResponse = fhirResponse)
        return
      }

      val httpRequest =
        Request
          .Builder()
          .url(request.url)
          .get()
          .addHeader("X-MGO-HEALTHCARE-PROVIDER-ID", request.medmijId ?: "none")
          .addHeader("X-MGO-DATASERVICE-ID", request.dataServiceId)
          .addHeader("X-MGO-DVA-TARGET", request.resourceEndpoint)
          .addHeader("Accept", "application/fhir+json; fhirVersion=${request.fhirVersion.stringValue}")
          .build()

      try {
        val response = okHttpClient.newCall(httpRequest).execute()

        if (response.isSuccessful) {
          // Get the response
          val responseBytes = response.body?.bytes() ?: "{}".toByteArray()

          // Store the response
          mgoByteArrayStorage.delete(cacheKey)
          mgoByteArrayStorage.save(name = cacheKey, content = responseBytes)

          // Update the cached response with success state
          val fhirResponse =
            FhirResponse.Success(
              request = request,
              cacheKey = cacheKey,
              isEmpty = isBundleEmpty(responseBytes.toString(Charsets.UTF_8)),
            )
          updateCachedFhirResponse(fhirResponse = fhirResponse)
        } else {
          // Update the cached response with error state
          val fhirResponse =
            FhirResponse.Error(
              request = request,
              type = FhirResponseErrorType.SERVER,
              error = IllegalStateException("Something went wrong with fetching the fhir resource"),
            )
          updateCachedFhirResponse(fhirResponse = fhirResponse)
        }
      } catch (e: IOException) {
        Timber.e(e, "Failed to parse fhir")
        // Update the cached response with error state
        val fhirResponse =
          FhirResponse.Error(
            request = request,
            type = FhirResponseErrorType.USER,
            error = e,
          )
        updateCachedFhirResponse(fhirResponse = fhirResponse)
      }
    }

    override suspend fun delete(organizationId: String) {
      mgoByteArrayStorage.delete(organizationId)
    }

    override suspend fun deleteFailed() {
      val newFhirResponses = cachedFhirResponses.value.toMutableList().also { list -> list.removeAll { it is FhirResponse.Error } }
      cachedFhirResponses.value = newFhirResponses
    }

    private fun isBundleEmpty(bundleJson: String): Boolean {
      val json = Json.parseToJsonElement(bundleJson).jsonObject
      val entry = json["entry"]?.jsonArray
      return entry.isNullOrEmpty()
    }

    override suspend fun fetchBinary(
      resourceEndpoint: String,
      url: String,
    ): Result<FhirBinary> {
      val request =
        Request
          .Builder()
          .url(url)
          .get()
          .addHeader("x-mgo-dva-target", resourceEndpoint)
          .addHeader("Accept", "application/fhir+json; fhirVersion=3.0s")
          .build()

      try {
        val response = okHttpClient.newCall(request).execute()

        if (response.isSuccessful) {
          val jsonResponse = response.body?.string() ?: "{}"
          val id =
            json
              .parseToJsonElement(jsonResponse)
              .jsonObject["id"]
              ?.jsonPrimitive
              ?.content ?: ""

          val contentType =
            json
              .parseToJsonElement(jsonResponse)
              .jsonObject["contentType"]
              ?.jsonPrimitive
              ?.content ?: ""

          val base64Content =
            json
              .parseToJsonElement(jsonResponse)
              .jsonObject["content"]
              ?.jsonPrimitive
              ?.content ?: ""
          val contentBytes = Base64.getDecoder().decode(base64Content)

          val file = File(context.cacheDir, "$id.pdf")
          file.writeBytes(contentBytes)

          val fhirBinary =
            FhirBinary(
              file = file,
              contentType = contentType,
            )

          return Result.success(fhirBinary)
        } else {
          return Result.failure(IllegalStateException("Something went wrong with fetching the fhir binary"))
        }
      } catch (e: IOException) {
        return Result.failure(e)
      }
    }

    private fun updateCachedFhirResponse(fhirResponse: FhirResponse) =
      cachedFhirResponses.getAndUpdate { cachedFhirResponses ->
        val updatedCachedFhirResponses = cachedFhirResponses.toMutableList()
        val existing =
          cachedFhirResponses.firstOrNull { response ->
            response.request.endpointId == fhirResponse.request.endpointId &&
              response.request.organizationId == fhirResponse.request.organizationId &&
              response.request.dataServiceId == fhirResponse.request.dataServiceId
          }
        if (existing == null) {
          updatedCachedFhirResponses.add(fhirResponse)
        } else {
          val index = updatedCachedFhirResponses.indexOf(existing)
          updatedCachedFhirResponses[index] = fhirResponse
        }
        updatedCachedFhirResponses
      }
  }
