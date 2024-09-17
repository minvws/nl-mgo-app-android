package nl.rijksoverheid.mgo.feature.organization.healthCategory

import com.squareup.moshi.JsonClass
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

@JsonClass(generateAdapter = true)
data class HealthCategoryScreenArguments(
    val filterOrganization: MgoOrganization?,
)
