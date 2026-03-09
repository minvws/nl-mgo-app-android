package nl.rijksoverheid.mgo.component.organization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias OrganizationId = String
typealias DataServiceId = String

@Serializable
data class Organization(
  val id: OrganizationId,
  @SerialName("display_name") val displayName: String,
  @SerialName("search_blob") val searchBlob: String,
  @SerialName("address_line") val addressLine: String?,
  @SerialName("city") val city: String?,
  @SerialName("added") val added: Boolean? = false,
  @SerialName("data_services") val dataServices: Map<DataServiceId, DataService>? = mapOf(),
)

@Serializable
data class DataService(
  @SerialName("auth_endpoint") val authEndpoint: String,
  @SerialName("token_endpoint") val tokenEndpoint: String,
  @SerialName("resource_endpoint") val resourceEndpoint: String,
)

val TEST_ORGANIZATION_1 =
  Organization(
    id = "1",
    displayName = "UMC Groningen",
    addressLine = "Hanzeplein 1",
    city = "Groningen",
    searchBlob = "",
    added = false,
    dataServices = mapOf(),
  )

val TEST_ORGANIZATION_2 =
  Organization(
    id = "2",
    displayName = "Amsterdam UMC",
    addressLine = "Hanzeplein 1",
    city = "Amsterdam",
    searchBlob = "",
    added = false,
    dataServices = mapOf(),
  )

val TEST_ORGANIZATION_3 =
  Organization(
    id = "3",
    displayName = "Maastricht UMC+",
    addressLine = "P. Debyelaan 25",
    city = "Maastricht",
    searchBlob = "",
    added = false,
    dataServices = mapOf(),
  )
