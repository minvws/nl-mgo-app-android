package nl.rijksoverheid.mgo.data.localisation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

/**
 * Represents the data service for a [MgoOrganization]. This is the source of where the get the health care data from.
 *
 * @param resourceEndpoint The endpoint of the source to get the health care data from.
 * @param type The name of the source.
 */
@Parcelize
@Serializable
data class MgoOrganizationDataService(
  val resourceEndpoint: String,
  val type: MgoOrganizationDataServiceType,
) : Parcelable

enum class MgoOrganizationDataServiceType {
  BGZ,
  GP,
  DOCUMENTS,
  VACCINATION,
  NOT_IMPLEMENTED,
}
