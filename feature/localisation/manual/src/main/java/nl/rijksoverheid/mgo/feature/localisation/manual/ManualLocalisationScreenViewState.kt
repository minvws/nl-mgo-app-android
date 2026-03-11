package nl.rijksoverheid.mgo.feature.localisation.manual

import nl.rijksoverheid.mgo.component.organization.MgoOrganization

data class ManualLocalisationScreenViewState(
  val loading: Boolean,
  val organizations: List<MgoOrganization>,
  val error: Boolean,
) {
  companion object {
    val initialState = ManualLocalisationScreenViewState(loading = false, organizations = listOf(), error = false)
  }
}
