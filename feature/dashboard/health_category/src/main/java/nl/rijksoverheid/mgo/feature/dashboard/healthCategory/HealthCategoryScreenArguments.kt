package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import com.squareup.moshi.JsonClass
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

@JsonClass(generateAdapter = true)
data class HealthCategoryScreenArguments(
    val category: HealthCareCategory,
    val filterOrganization: MgoOrganization?,
)
