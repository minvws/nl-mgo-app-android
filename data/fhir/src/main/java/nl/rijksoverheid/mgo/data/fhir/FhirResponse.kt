package nl.rijksoverheid.mgo.data.fhir

import nl.rijksoverheid.mgo.framework.storage.bytearray.MgoStorageCacheKey

sealed class FhirResponse(
  open val organizationId: String,
  open val dataServiceId: String,
  open val endpointId: String,
) {
  data class Success(
    override val organizationId: String,
    override val dataServiceId: String,
    override val endpointId: String,
    val cacheKey: MgoStorageCacheKey,
    val isEmpty: Boolean,
  ) : FhirResponse(organizationId, dataServiceId, endpointId)

  data class Error(
    override val organizationId: String,
    override val dataServiceId: String,
    override val endpointId: String,
    val error: Throwable,
  ) : FhirResponse(organizationId, dataServiceId, endpointId)
}

@Suppress("ktlint:standard:function-naming")
fun TEST_FHIR_RESPONSE_SUCCESS(isEmpty: Boolean = false) =
  FhirResponse.Success(
    organizationId = "1",
    dataServiceId = "1",
    endpointId = "1",
    cacheKey = "",
    isEmpty = isEmpty,
  )
