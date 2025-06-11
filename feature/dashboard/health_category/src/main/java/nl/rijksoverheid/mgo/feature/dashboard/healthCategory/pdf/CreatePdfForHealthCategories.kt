package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf

import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenListItemsGroup
import java.io.File

interface CreatePdfForHealthCategories {
  suspend operator fun invoke(
    category: HealthCareCategory,
    listItemGroups: List<HealthCategoryScreenListItemsGroup>,
  ): File
}
