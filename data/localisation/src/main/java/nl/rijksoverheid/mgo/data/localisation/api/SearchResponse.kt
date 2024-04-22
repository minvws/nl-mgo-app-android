package nl.rijksoverheid.mgo.data.localisation.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class SearchResponse(
    val organizations: List<Organization>,
) {
    @JsonClass(generateAdapter = true)
    data class Organization(
        @Json(name = "identification_value") val id: String,
        @Json(name = "display_name") val displayName: String?,
        val addresses: List<Address>,
    )

    @JsonClass(generateAdapter = true)
    data class Address(
        val address: String?,
        val city: String?,
        @Json(name = "postalcode") val postalCode: String?,
    )
}
