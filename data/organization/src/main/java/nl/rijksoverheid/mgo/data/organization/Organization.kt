package nl.rijksoverheid.mgo.data.organization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.MgoOrganizationDataService

typealias OrganizationId = String
typealias DataServiceId = String
typealias EndpointId = String

@Serializable
internal data class Organization(
  val id: OrganizationId,
  @SerialName("name") val displayName: String,
  @SerialName("search_blob") val searchBlob: String,
  @SerialName("address") val addressLine: String?,
  @SerialName("added") val added: Boolean? = false,
  @SerialName("data_services") val dataServices: List<DataService>? = null,
) {
  @Serializable
  data class DataService(
    val id: DataServiceId,
    @SerialName("auth_endpoint") val authEndpointId: String,
    @SerialName("token_endpoint") val tokenEndpointId: String,
    @SerialName("resource_endpoint") val resourceEndpointId: String,
  )
}

internal fun Organization.toMgoOrganization(
  supportedDataServiceIds: List<String>,
  getEndpoint: (id: EndpointId) -> String?,
) = MgoOrganization(
  id = id,
  medMijId = id,
  name = displayName,
  address = addressLine,
  dataServices =
    dataServices?.mapNotNull { dataService ->
      dataService.toMgoOrganizationDataService(isSupported = supportedDataServiceIds.contains(dataService.id), getEndpoint = getEndpoint)
    },
  added = added ?: false,
)

internal fun Organization.DataService.toMgoOrganizationDataService(
  isSupported: Boolean,
  getEndpoint: (id: EndpointId) -> String?,
): MgoOrganizationDataService? {
  val resourceEndpoint = getEndpoint(resourceEndpointId) ?: return null
  val authEndpoint = getEndpoint(authEndpointId) ?: return null
  val tokenEndpoint = getEndpoint(tokenEndpointId) ?: return null
  return MgoOrganizationDataService(
    id = id,
    resourceEndpoint = resourceEndpoint,
    authEndpoint = authEndpoint,
    tokenEndpoint = tokenEndpoint,
    isSupported = isSupported,
  )
}
