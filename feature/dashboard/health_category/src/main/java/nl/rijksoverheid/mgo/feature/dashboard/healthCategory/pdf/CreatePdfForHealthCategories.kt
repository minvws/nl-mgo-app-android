package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf

import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenListItemsGroup
import java.io.File

interface CreatePdfForHealthCategories {
  suspend operator fun invoke(
    category: HealthCareCategoryId,
    listItemGroups: List<HealthCategoryScreenListItemsGroup>,
  ): File
}
