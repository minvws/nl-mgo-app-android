package nl.rijksoverheid.mgo.data.localisation.models

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
val TEST_NOT_IMPLEMENTED_DATA_SERVICE =
  MgoOrganizationDataService(
    id = "999",
    resourceEndpoint = "",
    isSupported = false,
  )
