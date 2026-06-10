package nl.rijksoverheid.mgo.data.fhir

import nl.rijksoverheid.mgo.framework.storage.bytearray.MgoStorageCacheKey

sealed class FhirResponse(
  open val request: FhirRequest,
) {
  data class Success(
    override val request: FhirRequest,
    val cacheKey: MgoStorageCacheKey,
    val isEmpty: Boolean,
  ) : FhirResponse(request)

  data class Error(
    override val request: FhirRequest,
    val type: FhirResponseErrorType,
    val error: Throwable,
  ) : FhirResponse(request)
}

enum class FhirResponseErrorType {
  USER,
  SERVER,
}

@Suppress("ktlint:standard:function-naming")
fun TEST_FHIR_RESPONSE_SUCCESS(isEmpty: Boolean = false) =
  FhirResponse.Success(
    request = TEST_FHIR_REQUEST,
    cacheKey = "",
    isEmpty = isEmpty,
  )

val TEST_FHIR_RESPONSE_USER_ERROR =
  FhirResponse.Error(
    type = FhirResponseErrorType.USER,
    request = TEST_FHIR_REQUEST,
    error = IllegalStateException("Something went wrong"),
  )

val TEST_FHIR_RESPONSE_SERVER_ERROR =
  FhirResponse.Error(
    type = FhirResponseErrorType.SERVER,
    request = TEST_FHIR_REQUEST,
    error = IllegalStateException("Something went wrong"),
  )
