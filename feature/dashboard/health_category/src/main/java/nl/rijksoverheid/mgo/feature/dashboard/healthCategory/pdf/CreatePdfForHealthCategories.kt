package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf

import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenListItemsGroup
import java.io.File

interface CreatePdfForHealthCategories {
  suspend operator fun invoke(
    category: HealthCategoryGroup.HealthCategory,
    listItemGroups: List<HealthCategoryScreenListItemsGroup>,
  ): File
}
