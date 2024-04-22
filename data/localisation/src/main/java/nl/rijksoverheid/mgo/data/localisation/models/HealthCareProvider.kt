package nl.rijksoverheid.mgo.data.localisation.models

import nl.rijksoverheid.mgo.data.localisation.api.SearchResponse

data class HealthCareProvider(
    val id: String,
    val name: String,
    val city: String?,
    val address: String?,
    val postalCode: String?,
)

val TEST_HEALTH_CARE_PROVIDER =
    HealthCareProvider(
        id = "1",
        name = "Tandarts Tandje Erbij",
        city = "Roermond",
        address = "Boorplatform 5",
        postalCode = "1234AB",
    )

internal fun SearchResponse.toHealthCareProviders(): List<HealthCareProvider> {
    return organizations.mapNotNull { organization ->
        val name = organization.displayName ?: return@mapNotNull null
        val address = organization.addresses.firstOrNull() ?: return@mapNotNull null
        HealthCareProvider(
            id = organization.id,
            name = name,
            city = address.city,
            address = address.address,
            postalCode = address.postalCode,
        )
    }
}
