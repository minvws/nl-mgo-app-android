package nl.rijksoverheid.mgo.component.organization

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

typealias MgoOrganizationDataServiceId = String

@Parcelize
@Serializable
data class MgoOrganizationDataService(
  val id: MgoOrganizationDataServiceId,
  val resourceEndpoint: String,
  val isSupported: Boolean,
) : Parcelable

val TEST_BGZ_DATA_SERVICE = MgoOrganizationDataService(id = "48", resourceEndpoint = "", isSupported = true)
val TEST_GP_DATA_SERVICE = MgoOrganizationDataService(id = "49", resourceEndpoint = "", isSupported = true)
val TEST_DOCUMENTS_DATA_SERVICE = MgoOrganizationDataService(id = "51", resourceEndpoint = "", isSupported = true)
