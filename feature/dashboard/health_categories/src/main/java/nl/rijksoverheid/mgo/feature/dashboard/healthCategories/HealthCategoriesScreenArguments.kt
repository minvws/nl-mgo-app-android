package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import com.squareup.moshi.JsonClass
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

@JsonClass(generateAdapter = true)
data class HealthCategoriesScreenArguments(
    val filterOrganization: MgoOrganization?,
)
