package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

/**
 * The view state for [HealthCategoriesScreen].
 *
 * @param name The name of user.
 * @param providers List of added [MgoOrganization].
 * @param automaticLocalisationEnabled If the automatic localisation feature is enabled.
 */
data class HealthCategoriesScreenViewState(
  val name: String,
  val providers: List<MgoOrganization>,
  val automaticLocalisationEnabled: Boolean,
) {
  companion object {
    fun initialState(
      providers: List<MgoOrganization>,
      automaticLocalisationEnabled: Boolean,
    ) = HealthCategoriesScreenViewState(
      name = "Wendy de Bruijn",
      providers = providers,
      automaticLocalisationEnabled = automaticLocalisationEnabled,
    )
  }
}
