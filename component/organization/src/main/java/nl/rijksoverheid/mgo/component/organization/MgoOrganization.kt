package nl.rijksoverheid.mgo.component.organization

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

typealias MgoOrganizationId = String
typealias MgoOrganizationMedMijId = String

@Parcelize
@Serializable
data class MgoOrganization(
  val id: MgoOrganizationId,
  val medMijId: MgoOrganizationMedMijId?,
  val name: String,
  val address: String?,
  val category: String?,
  val added: Boolean,
  val dataServices: List<MgoOrganizationDataService>,
) : Parcelable

fun MgoOrganization.getDocumentsResourceEndpoint(): String? = dataServices.firstOrNull { service -> service.id == "51" }?.resourceEndpoint

val TEST_MGO_ORGANIZATION =
  MgoOrganization(
    id = "1",
    medMijId = "1",
    name = "Tandarts Tandje Erbij",
    address = "Boorplatform 5\r\n1234AB Roermond",
    category = "Tandarts",
    added = false,
    dataServices = listOf(TEST_BGZ_DATA_SERVICE),
  )
