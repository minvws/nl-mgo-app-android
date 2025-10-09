package nl.rijksoverheid.mgo.data.fhir

sealed class FhirResponse(
  open val organizationId: String,
  open val dataServiceId: String,
  open val endpointId: String,
) {
  data class Success(
    override val organizationId: String,
    override val dataServiceId: String,
    override val endpointId: String,
    val jsonSource: FhirResponseJsonSource,
    val isEmpty: Boolean,
  ) : FhirResponse(organizationId, dataServiceId, endpointId)

  data class Error(
    override val organizationId: String,
    override val dataServiceId: String,
    override val endpointId: String,
    val error: Throwable,
  ) : FhirResponse(organizationId, dataServiceId, endpointId)
}
