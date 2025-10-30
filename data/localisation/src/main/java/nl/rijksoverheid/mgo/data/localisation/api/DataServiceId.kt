package nl.rijksoverheid.mgo.data.localisation.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

typealias DataServiceId = String

@Serializable
class SearchResponse(
  val organizations: List<Organization>,
) {
  @Serializable
  data class Organization(
    @SerialName("identification") val id: String,
    @SerialName("medmij_id") val medMijId: String?,
    @SerialName("display_name") val displayName: String?,
    val addresses: List<Address>,
    val types: List<Types>,
    @SerialName("data_services") val dataServices: List<DataService>,
  )

  @Serializable
  data class Address(
    val address: String?,
    val city: String?,
    @SerialName("postalcode") val postalCode: String?,
  )

  @Serializable
  data class Types(
    @SerialName("display_name") val displayName: String?,
  )

  @Serializable
  data class DataService(
    val id: DataServiceId,
    val roles: List<Role>,
  ) {
    @Serializable
    data class Role(
      @SerialName("resource_endpoint") val resourceEndpoint: String,
    )
  }
}
