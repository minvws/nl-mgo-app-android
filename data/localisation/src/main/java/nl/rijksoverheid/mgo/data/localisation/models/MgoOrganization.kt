package nl.rijksoverheid.mgo.data.localisation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import nl.rijksoverheid.mgo.data.api.load.DATA_SERVICE_BGZ
import nl.rijksoverheid.mgo.data.api.load.DATA_SERVICE_DOCUMENTS
import nl.rijksoverheid.mgo.data.api.load.DATA_SERVICE_GP
import nl.rijksoverheid.mgo.data.api.load.DATA_SERVICE_VACCINATION
import nl.rijksoverheid.mgo.data.api.load.SearchResponse

typealias MgoOrganizationId = String

/**
 * Represents a health care provider.
 *
 * @param id The id of the health care provider.
 * @param name The name of the health care provider.
 * @param address The address of the health care provider.
 * @param category The category of the health care provider.
 * @param added If this health care provider has been added.
 * @param dataServices A list of [MgoOrganizationDataService] associated to the health care provider.
 */
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

fun MgoOrganization.getDocumentsResourceEndpoint(): String? {
  return dataServices.firstOrNull { service -> service.type == MgoOrganizationDataServiceType.DOCUMENTS }?.resourceEndpoint
}

val TEST_BGZ_DATA_SERVICE = MgoOrganizationDataService(resourceEndpoint = "", type = MgoOrganizationDataServiceType.BGZ)
val TEST_GP_DATA_SERVICE = MgoOrganizationDataService(resourceEndpoint = "", type = MgoOrganizationDataServiceType.GP)
val TEST_DOCUMENTS_DATA_SERVICE = MgoOrganizationDataService(resourceEndpoint = "", type = MgoOrganizationDataServiceType.DOCUMENTS)
val TEST_VACCINATION_DATA_SERVICE = MgoOrganizationDataService(resourceEndpoint = "", type = MgoOrganizationDataServiceType.VACCINATION)

val TEST_NOT_IMPLEMENTED_DATA_SERVICE =
  MgoOrganizationDataService(
    resourceEndpoint = "",
    type = MgoOrganizationDataServiceType.NOT_IMPLEMENTED,
  )

val TEST_MGO_ORGANIZATION =
  MgoOrganization(
    id = "1",
    name = "Tandarts Tandje Erbij",
    address = "Boorplatform 5\r\n1234AB Roermond",
    category = "Tandarts",
    added = false,
    dataServices = listOf(TEST_BGZ_DATA_SERVICE),
  )

internal fun SearchResponse.Organization.toMgoOrganization(added: Boolean): MgoOrganization {
  return MgoOrganization(
    id = id,
    name = displayName ?: "",
    address = addresses.firstOrNull()?.address,
    category = types.firstOrNull()?.displayName,
    added = added,
    dataServices =
      dataServices.map { dataService ->
        when (dataService.id) {
          DATA_SERVICE_BGZ ->
            MgoOrganizationDataService(
              resourceEndpoint = dataService.roles.first().resourceEndpoint,
              MgoOrganizationDataServiceType.BGZ,
            )

          DATA_SERVICE_GP ->
            MgoOrganizationDataService(
              resourceEndpoint = dataService.roles.first().resourceEndpoint,
              MgoOrganizationDataServiceType.GP,
            )

          DATA_SERVICE_DOCUMENTS ->
            MgoOrganizationDataService(
              resourceEndpoint = dataService.roles.first().resourceEndpoint,
              MgoOrganizationDataServiceType.DOCUMENTS,
            )

          DATA_SERVICE_VACCINATION ->
            MgoOrganizationDataService(
              resourceEndpoint = dataService.roles.first().resourceEndpoint,
              MgoOrganizationDataServiceType.VACCINATION,
            )

          else ->
            MgoOrganizationDataService(
              resourceEndpoint = dataService.roles.first().resourceEndpoint,
              MgoOrganizationDataServiceType.NOT_IMPLEMENTED,
            )
        }
      },
  )
}
