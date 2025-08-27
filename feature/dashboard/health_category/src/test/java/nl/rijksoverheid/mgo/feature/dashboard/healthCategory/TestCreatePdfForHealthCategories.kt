package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoryId
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf.CreatePdfForHealthCategories
import java.io.File

internal class TestCreatePdfForHealthCategories : CreatePdfForHealthCategories {
  private var pdfGenerated: Boolean = false

  override suspend fun invoke(
    category: HealthCareCategoryId,
    listItemGroups: List<HealthCategoryScreenListItemsGroup>,
  ): File {
    pdfGenerated = true
    return File("")
  }

  fun assertPdfGenerated() = pdfGenerated

  fun reset() {
    pdfGenerated = false
  }
}
