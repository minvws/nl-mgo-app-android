package nl.rijksoverheid.mgo.data.organization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.MgoOrganizationDataService

typealias OrganizationId = String
typealias DataServiceId = String

@Serializable
internal data class Organization(
  val id: OrganizationId,
  @SerialName("display_name") val displayName: String,
  @SerialName("search_blob") val searchBlob: String,
  @SerialName("address_line") val addressLine: String?,
  @SerialName("city") val city: String?,
  @SerialName("added") val added: Boolean? = false,
  @SerialName("data_services") val dataServices: Map<DataServiceId, DataService>? = mapOf(),
) {
  @Serializable
  data class DataService(
    @SerialName("auth_endpoint") val authEndpoint: String,
    @SerialName("token_endpoint") val tokenEndpoint: String,
    @SerialName("resource_endpoint") val resourceEndpoint: String,
  )
}

internal fun Organization.toMgoOrganization(supportedDataServiceIds: List<String>) =
  MgoOrganization(
    id = id,
    medMijId = id,
    name = displayName,
    address =
      buildString {
        val address = addressLine
        if (address != null) {
          append(address)
          append(", ")
        }
        append(city)
      },
    dataServices =
      dataServices?.map { it.value.toMgoOrganizationDataService(id = it.key, isSupported = supportedDataServiceIds.contains(it.key)) } ?: listOf(),
    added = added ?: false,
  )

internal fun Organization.DataService.toMgoOrganizationDataService(
  id: String,
  isSupported: Boolean,
) = MgoOrganizationDataService(
  id = id,
  resourceEndpoint = resourceEndpoint,
  authEndpoint = authEndpoint,
  tokenEndpoint = tokenEndpoint,
  isSupported = isSupported,
)

internal fun MgoOrganizationDataService.toOrganizationDataService() =
  Organization.DataService(
    authEndpoint = authEndpoint,
    resourceEndpoint = resourceEndpoint,
    tokenEndpoint = tokenEndpoint,
  )

internal fun MgoOrganization.toOrganization() =
  Organization(
    id = id,
    displayName = name,
    searchBlob = "",
    addressLine = address?.split(", ")?.getOrNull(0) ?: "",
    city = address?.split(", ")?.getOrNull(1) ?: "",
    added = added,
    dataServices = dataServices.associate { dataService -> dataService.id to dataService.toOrganizationDataService() },
  )
