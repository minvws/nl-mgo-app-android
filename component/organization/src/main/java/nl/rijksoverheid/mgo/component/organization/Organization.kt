package nl.rijksoverheid.mgo.component.organization

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias OrganizationId = String

@Serializable
data class Organization(
  val id: OrganizationId,
  @SerialName("display_name") val displayName: String,
  @SerialName("search_blob") val searchBlob: String,
  @SerialName("address_line") val addressLine: String?,
  @SerialName("city") val city: String?,
  @SerialName("added") val added: Boolean? = false,
)

val TEST_ORGANIZATION_1 =
  Organization(
    id = "1",
    displayName = "UMC Groningen",
    addressLine = "Hanzeplein 1",
    city = "Groningen",
    searchBlob = "",
    added = false,
  )

val TEST_ORGANIZATION_2 =
  Organization(
    id = "1",
    displayName = "Amsterdam UMC",
    addressLine = "Hanzeplein 1",
    city = "Amsterdam",
    searchBlob = "",
    added = false,
  )

val TEST_ORGANIZATION_3 =
  Organization(
    id = "1",
    displayName = "Maastricht UMC+",
    addressLine = "P. Debyelaan 25",
    city = "Maastricht",
    searchBlob = "",
    added = false,
  )
