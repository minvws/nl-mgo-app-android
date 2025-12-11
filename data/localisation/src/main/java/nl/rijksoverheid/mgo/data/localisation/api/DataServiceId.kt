package nl.rijksoverheid.mgo.data.localisation.api

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.MgoOrganizationDataService

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

internal fun SearchResponse.Organization.toMgoOrganization(
  added: Boolean,
  supportedDataServiceIds: List<String>,
): MgoOrganization =
  MgoOrganization(
    id = id,
    medMijId = medMijId,
    name = displayName ?: "",
    address = addresses.firstOrNull()?.address,
    category = types.firstOrNull()?.displayName,
    added = added,
    dataServices =
      dataServices.map { dataService ->
        MgoOrganizationDataService(
          id = dataService.id,
          resourceEndpoint = dataService.roles.first().resourceEndpoint,
          isSupported = supportedDataServiceIds.contains(dataService.id),
        )
      },
  )
