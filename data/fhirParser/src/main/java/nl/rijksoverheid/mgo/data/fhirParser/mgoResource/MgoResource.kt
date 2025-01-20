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
    val jsonBase64: String,
) : Parcelable

val TEST_MGO_RESOURCE =
    MgoResource(
        referenceId = "1",
        profile = "profile",
        jsonBase64 = "",
    )

fun String.toMgoResource(jsonBase64: String): MgoResource {
    val json = JSONObject(this)
    return MgoResource(
        referenceId = json.getString("referenceId"),
        profile = json.getString("profile"),
        jsonBase64 = jsonBase64,
    )
}
