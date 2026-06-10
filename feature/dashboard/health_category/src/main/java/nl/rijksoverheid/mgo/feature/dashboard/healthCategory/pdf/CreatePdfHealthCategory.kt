package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf

import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import java.io.File

interface CreatePdfHealthCategory {
  suspend operator fun invoke(
    mgoResources: List<MgoResource>,
    category: HealthCategoryGroup.HealthCategory,
  ): File
}
