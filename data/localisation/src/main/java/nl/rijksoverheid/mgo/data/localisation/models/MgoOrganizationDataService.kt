package nl.rijksoverheid.mgo.data.localisation.models

import android.os.Parcelable
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

@Parcelize
@Serializable
@JsonClass(generateAdapter = true)
data class MgoOrganizationDataService(
    val resourceEndpoint: String,
    val type: MgoOrganizationDataServiceType,
) : Parcelable

@JsonClass(generateAdapter = false)
enum class MgoOrganizationDataServiceType {
    BGZ,
    GP,
    NOT_IMPLEMENTED,
}
