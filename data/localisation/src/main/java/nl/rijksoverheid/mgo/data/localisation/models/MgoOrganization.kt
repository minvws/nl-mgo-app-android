package nl.rijksoverheid.mgo.data.localisation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.rijksoverheid.mgo.data.localisation.api.SearchResponse

typealias MgoOrganizationId = String

@Parcelize
@Serializable
data class MgoOrganization(
  val id: MgoOrganizationId,
  val name: String,
  val address: String?,
  val category: String?,
  val added: Boolean,
  val dataServices: List<MgoOrganizationDataService>,
) : Parcelable

fun MgoOrganization.getDocumentsResourceEndpoint(): String? = dataServices.firstOrNull { service -> service.id == "61" }?.resourceEndpoint

val TEST_MGO_ORGANIZATION =
  MgoOrganization(
    id = "1",
    name = "Tandarts Tandje Erbij",
    address = "Boorplatform 5\r\n1234AB Roermond",
    category = "Tandarts",
    added = false,
    dataServices = listOf(TEST_BGZ_DATA_SERVICE),
  )

internal fun SearchResponse.Organization.toMgoOrganization(
  added: Boolean,
  supportedDataServiceIds: List<String>,
): MgoOrganization =
  MgoOrganization(
    id = id,
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
