package nl.rijksoverheid.mgo.feature.dashboard.organizations

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

/**
 * The view state for [OrganizationsScreen].
 *
 * @param organizations The list of added organizations.
 * @param automaticLocalisationEnabled True if automatic localisation is enabled.
 */
data class OrganizationsViewState(
  val organizations: List<MgoOrganization>,
  val automaticLocalisationEnabled: Boolean,
) {
  companion object {
    fun initialState(
      organizations: List<MgoOrganization>,
      automaticLocalisationEnabled: Boolean,
    ) = OrganizationsViewState(
      organizations = organizations,
      automaticLocalisationEnabled = automaticLocalisationEnabled,
    )
  }
}
