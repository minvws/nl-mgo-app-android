package nl.rijksoverheid.mgo.data.localisation.models

import com.squareup.moshi.JsonClass
import nl.rijksoverheid.mgo.data.api.load.SearchResponse

@JsonClass(generateAdapter = true)
data class MgoOrganization(
    val id: String,
    val name: String,
    val address: String?,
    val category: String?,
    val added: Boolean,
    val resourceEndpoint: String,
)

val TEST_MGO_ORGANIZATION =
    MgoOrganization(
        id = "1",
        name = "Tandarts Tandje Erbij",
        address = "Boorplatform 5\r\n1234AB Roermond",
        category = "Tandarts",
        added = false,
        resourceEndpoint = "https://www.google.nl",
    )

internal fun SearchResponse.Organization.toMgoOrganization(added: Boolean): MgoOrganization {
    return MgoOrganization(
        id = id,
        name = displayName ?: "",
        address = addresses.firstOrNull()?.address,
        category = types.firstOrNull()?.displayName,
        added = added,
        resourceEndpoint = dataServices.first { dataService -> dataService.id == 48 }.roles.first().resourceEndpoint,
    )
}
