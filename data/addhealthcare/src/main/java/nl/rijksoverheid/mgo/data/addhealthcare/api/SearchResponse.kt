package nl.rijksoverheid.mgo.data.search.api

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
internal class SearchResponse(
    val organisations: List<Organisation>,
) {
    @JsonClass(generateAdapter = true)
    data class Organisation(
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
