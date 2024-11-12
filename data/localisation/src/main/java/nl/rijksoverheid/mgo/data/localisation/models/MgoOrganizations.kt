package nl.rijksoverheid.mgo.data.localisation.models

import kotlinx.serialization.Serializable

@Serializable
data class MgoOrganizations(
    val providers: List<MgoOrganization>,
)
