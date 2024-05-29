package nl.rijksoverheid.mgo.data.api.load

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class SearchResponse(
    val organizations: List<Organization>,
) {
    @JsonClass(generateAdapter = true)
    data class Organization(
        @Json(name = "identification_value") val id: String,
        @Json(name = "display_name") val displayName: String?,
        val addresses: List<Address>,
        val types: List<Types>,
    )

    @JsonClass(generateAdapter = true)
    data class Address(
        val address: String?,
        val city: String?,
        @Json(name = "postalcode") val postalCode: String?,
    )

    @JsonClass(generateAdapter = true)
    data class Types(
        @Json(name = "display_name") val displayName: String?,
    )
}
