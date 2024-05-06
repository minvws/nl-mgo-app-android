package nl.rijksoverheid.mgo.data.localisation.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class HealthCareProviders(
    val providers: List<HealthCareProvider>,
)
