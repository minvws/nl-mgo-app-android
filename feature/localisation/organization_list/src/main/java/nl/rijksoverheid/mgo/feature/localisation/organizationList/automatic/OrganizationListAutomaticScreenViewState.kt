package nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

/**
 * The view state for [OrganizationListAutomaticSearchScreen].
 *
 * @param loading If the health care providers are loading.
 * @param results The health care providers to display.
 * @param error If there was an error retrieving the health care providers.
 */
data class OrganizationListAutomaticScreenViewState(
  val loading: Boolean,
  val results: List<MgoOrganization>,
  val error: Throwable?,
) {
  companion object {
    val initialState =
      OrganizationListAutomaticScreenViewState(
        loading = true,
        results = listOf(),
        error = null,
      )
  }
}
