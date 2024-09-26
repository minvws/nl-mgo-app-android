package nl.rijksoverheid.mgo.data.localisation.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MgoOrganizationDataService(
    val resourceEndpoint: String,
    val type: MgoOrganizationDataServiceType
)

enum class MgoOrganizationDataServiceType {
    BGZ,
    GP
}
