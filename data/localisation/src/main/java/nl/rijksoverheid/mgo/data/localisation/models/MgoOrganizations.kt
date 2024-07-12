package nl.rijksoverheid.mgo.data.localisation.models

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MgoOrganizations(
    val providers: List<MgoOrganization>,
)
