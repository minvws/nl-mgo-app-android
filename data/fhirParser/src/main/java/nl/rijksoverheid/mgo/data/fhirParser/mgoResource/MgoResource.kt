package nl.rijksoverheid.mgo.data.fhirParser.mgoResource

import android.os.Parcelable
import org.json.JSONObject
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable

typealias MgoResourceReferenceId = String
typealias MgoResourceProfile = String

@Serializable
@Parcelize
data class MgoResource(
    val referenceId: MgoResourceReferenceId,
    val profile: MgoResourceProfile,
    val json: String,
) : Parcelable

val TEST_MGO_RESOURCE =
    MgoResource(
        referenceId = "1",
        profile = "profile",
        json = "",
    )

fun String.toMgoResource(): MgoResource {
    val json = JSONObject(this)
    return MgoResource(
        referenceId = json.getString("referenceId"),
        profile = json.getString("profile"),
        json = this,
    )
}
