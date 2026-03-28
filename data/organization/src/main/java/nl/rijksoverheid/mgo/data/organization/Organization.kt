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
  @SerialName("name") val displayName: String,
  @SerialName("search_blob") val searchBlob: String,
  @SerialName("address") val addressLine: String?,
  @SerialName("added") val added: Boolean? = false,
  @SerialName("data_services") val dataServices: List<DataService>? = null,
) {
  @Serializable
  data class DataService(
    val id: DataServiceId,
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
    address = addressLine,
    dataServices =
      dataServices?.map { dataService ->
        dataService.toMgoOrganizationDataService(isSupported = supportedDataServiceIds.contains(dataService.id))
      },
    added = added ?: false,
  )

internal fun Organization.DataService.toMgoOrganizationDataService(isSupported: Boolean) =
  MgoOrganizationDataService(
    id = id,
    resourceEndpoint = resourceEndpoint,
    authEndpoint = authEndpoint,
    tokenEndpoint = tokenEndpoint,
    isSupported = isSupported,
  )

internal fun MgoOrganizationDataService.toOrganizationDataService() =
  Organization.DataService(
    id = id,
    authEndpoint = authEndpoint,
    resourceEndpoint = resourceEndpoint,
    tokenEndpoint = tokenEndpoint,
  )

internal fun MgoOrganization.toOrganization() =
  Organization(
    id = id,
    displayName = name,
    searchBlob = "",
    addressLine = address,
    added = added,
    dataServices = dataServices?.map { dataService -> dataService.toOrganizationDataService() },
  )
