package nl.rijksoverheid.mgo.data.localisation.models

import com.squareup.moshi.JsonClass
import nl.rijksoverheid.mgo.data.localisation.api.SearchResponse

@JsonClass(generateAdapter = true)
data class HealthCareProvider(
    val id: String,
    val name: String,
    val address: String?,
    val category: String?,
    val added: Boolean,
)

val TEST_HEALTH_CARE_PROVIDER =
    HealthCareProvider(
        id = "1",
        name = "Tandarts Tandje Erbij",
        address = "Boorplatform 5\r\n1234AB Roermond",
        category = "Tandarts",
        added = false,
    )

internal fun SearchResponse.Organization.toHealthCareProvider(added: Boolean): HealthCareProvider {
    return HealthCareProvider(
        id = id,
        name = displayName ?: "",
        address = addresses.firstOrNull()?.address,
        category = types.firstOrNull()?.displayName,
        added = added,
    )
}
