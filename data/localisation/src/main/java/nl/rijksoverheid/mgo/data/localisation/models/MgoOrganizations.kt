package nl.rijksoverheid.mgo.data.localisation.models

import kotlinx.serialization.Serializable

/**
 * Represents a list of [MgoOrganization].
 *
 * @param providers The list of [MgoOrganization].
 */
@Serializable
data class MgoOrganizations(
  val providers: List<MgoOrganization>,
)
