package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategory
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
  val categories: List<HealthCareCategory>,
  val favorites: List<HealthCareCategory>,
  val automaticLocalisationEnabled: Boolean,
) {
  companion object {
    fun initialState(
      categories: List<HealthCareCategory>,
      providers: List<MgoOrganization>,
      automaticLocalisationEnabled: Boolean,
    ) = HealthCategoriesScreenViewState(
      name = "Wendy de Bruijn",
      providers = providers,
      automaticLocalisationEnabled = automaticLocalisationEnabled,
      categories = categories,
      favorites = categories.filter { category -> category.favoritePosition != -1 },
    )
  }
}
