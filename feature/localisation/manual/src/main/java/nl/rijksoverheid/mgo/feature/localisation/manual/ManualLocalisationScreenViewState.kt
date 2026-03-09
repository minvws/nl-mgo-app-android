package nl.rijksoverheid.mgo.feature.localisation.manual

import nl.rijksoverheid.mgo.component.organization.Organization

data class ManualLocalisationScreenViewState(
  val loading: Boolean,
  val organizations: List<OrganizationUi>,
  val error: Boolean,
) {
  companion object {
    val initialState = ManualLocalisationScreenViewState(loading = false, organizations = listOf(), error = false)
  }
}
