package nl.rijksoverheid.mgo.feature.localisation.organizationList.manual

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

/**
 * The view state for [OrganizationListManualScreen].
 *
 * @param loading If the health care providers are loading.
 * @param name The name of the health care provider to search for.
 * @param city The city of the health care provider to search for.
 * @param results The list of health care providers to show. Is empty if error is not null and this list is empty.
 * @param error If there was an error retrieving the health care providers.
 */
data class OrganizationListManualScreenViewState(
  val loading: Boolean,
  val name: String,
  val city: String,
  val results: List<MgoOrganization>,
  val error: Throwable?,
) {
  companion object {
    fun initialState(
      name: String,
      city: String,
    ): OrganizationListManualScreenViewState {
      return OrganizationListManualScreenViewState(
        loading = true,
        name = name,
        city = city,
        results = listOf(),
        error = null,
      )
    }
  }
}
