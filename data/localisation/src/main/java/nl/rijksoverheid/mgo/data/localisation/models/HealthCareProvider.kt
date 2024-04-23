package nl.rijksoverheid.mgo.data.localisation.models

import com.squareup.moshi.JsonClass
import nl.rijksoverheid.mgo.data.localisation.api.SearchResponse

@JsonClass(generateAdapter = true)
data class HealthCareProvider(
    val id: String,
    val name: String,
    val address: String?,
    val category: String?,
)

val TEST_HEALTH_CARE_PROVIDER =
    HealthCareProvider(
        id = "1",
        name = "Tandarts Tandje Erbij",
        address = "Boorplatform 5\r\n1234AB Roermond",
        category = "Tandarts",
    )

internal fun SearchResponse.toHealthCareProviders(): List<HealthCareProvider> {
    return organizations.mapNotNull { organization ->
        val name = organization.displayName ?: return@mapNotNull null
        val address = organization.addresses.firstOrNull() ?: return@mapNotNull null
        HealthCareProvider(
            id = organization.id,
            name = name,
            address = address.address,
            category = organization.types.firstOrNull()?.displayName,
        )
    }
}
