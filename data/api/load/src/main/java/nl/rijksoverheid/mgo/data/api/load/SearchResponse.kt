package nl.rijksoverheid.mgo.data.api.load

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

typealias DataServiceId = Int

const val DATA_SERVICE_BGZ: DataServiceId = 48
const val DATA_SERVICE_GP: DataServiceId = 49

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
        @Json(name = "data_services") val dataServices: List<DataService>,
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

    @JsonClass(generateAdapter = true)
    data class DataService(
        val id: DataServiceId,
        val roles: List<Role>,
    ) {
        @JsonClass(generateAdapter = true)
        data class Role(
            @Json(name = "resource_endpoint") val resourceEndpoint: String,
        )
    }
}
