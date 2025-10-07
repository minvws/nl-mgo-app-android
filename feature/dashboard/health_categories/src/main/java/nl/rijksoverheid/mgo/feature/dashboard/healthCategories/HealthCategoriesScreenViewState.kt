package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
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
  val groups: List<HealthCategoryGroup>,
  val automaticLocalisationEnabled: Boolean,
) {
  companion object {
    fun initialState(
      groups: List<HealthCategoryGroup>,
      providers: List<MgoOrganization>,
      automaticLocalisationEnabled: Boolean,
    ) = HealthCategoriesScreenViewState(
      name = "Wendy de Bruijn",
      providers = providers,
      automaticLocalisationEnabled = automaticLocalisationEnabled,
      groups = groups,
    )
  }
}
