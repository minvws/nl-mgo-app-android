package nl.rijksoverheid.mgo.data.fhir

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.getAndUpdate
import kotlinx.coroutines.flow.map
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion
import okhttp3.OkHttpClient
import okhttp3.Request
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FhirRepository
  @Inject
  constructor(
    private val okHttpClient: OkHttpClient,
    private val fhirResponseJsonStore: FhirResponseJsonStore,
  ) {
    private val cachedFhirResponses = MutableStateFlow<List<FhirResponse>>(listOf())

    fun observe(
      organizationId: String,
      dataServiceId: String,
      endpointId: String,
    ): Flow<FhirResponse?> =
      cachedFhirResponses.map { responses ->
        responses.firstOrNull { response ->
          response.organizationId == organizationId && response.dataServiceId == dataServiceId &&
            response.endpointId == endpointId
        }
      }

    suspend fun fetch(
      organizationId: String,
      dataServiceId: String,
      endpointId: String,
      resourceEndpoint: String,
      fhirVersion: FhirVersion,
      url: String,
    ) {
      val request =
        Request
          .Builder()
          .url(url)
          .get()
          .addHeader("x-mgo-dva-target", resourceEndpoint)
          .addHeader("Accept", "application/fhir+json; fhirVersion=${fhirVersion.stringValue}")
          .build()

      val response = okHttpClient.newCall(request).execute()

      if (response.isSuccessful) {
        // Get the response
        val json = response.body?.string() ?: "{}"

        // Store the response
        val jsonSource = fhirResponseJsonStore.store(organizationId = organizationId, dataServiceId = dataServiceId, endpointId = endpointId, json = json)

        // Update the cached response with success state
        val fhirResponse =
          FhirResponse.Success(
            organizationId = organizationId,
            dataServiceId = dataServiceId,
            endpointId = endpointId,
            jsonSource = jsonSource,
          )
        updateCachedFhirResponse(fhirResponse = fhirResponse)
      } else {
        // Update the cached response with error state
        val fhirResponse =
          FhirResponse.Error(
            organizationId = organizationId,
            dataServiceId = dataServiceId,
            endpointId = endpointId,
            error = IllegalStateException("Something went wrong with fetching the fhir resource"),
          )
        updateCachedFhirResponse(fhirResponse = fhirResponse)
      }
    }

    private fun updateCachedFhirResponse(fhirResponse: FhirResponse) =
      cachedFhirResponses.getAndUpdate { cachedFhirResponses ->
        val updatedCachedFhirResponses = cachedFhirResponses.toMutableList()
        val existing =
          cachedFhirResponses.firstOrNull { response ->
            response.endpointId == fhirResponse.endpointId &&
              response.organizationId == fhirResponse.organizationId
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
