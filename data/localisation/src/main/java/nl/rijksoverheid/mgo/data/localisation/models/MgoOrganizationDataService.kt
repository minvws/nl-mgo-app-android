package nl.rijksoverheid.mgo.data.localisation.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

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
