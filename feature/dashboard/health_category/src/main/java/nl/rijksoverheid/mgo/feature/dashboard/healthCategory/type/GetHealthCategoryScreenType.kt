package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.type

import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import javax.inject.Inject

internal class GetHealthCategoryScreenType
  @Inject
  constructor() {
    operator fun invoke(category: HealthCategoryGroup.HealthCategory): HealthCategoryScreenType =
      when (category.id) {
        "documents" -> HealthCategoryScreenType.DATE
        else -> HealthCategoryScreenType.SUBCATEGORY
      }
  }
