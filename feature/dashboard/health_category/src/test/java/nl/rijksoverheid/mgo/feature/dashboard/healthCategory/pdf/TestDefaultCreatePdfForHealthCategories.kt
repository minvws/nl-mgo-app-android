package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf

import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.TEST_MGO_RESOURCE
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.TestUiSchemaMapper
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenListItem
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenListItemsGroup
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@RunWith(RobolectricTestRunner::class)
internal class TestDefaultCreatePdfForHealthCategories {
  private val createPdf =
    DefaultCreatePdfForHealthCategories(
      context = ApplicationProvider.getApplicationContext(),
      clock = Clock.fixed(Instant.parse("2000-01-01T10:01:00.00Z"), ZoneOffset.UTC),
      uiSchemaMapper = TestUiSchemaMapper(),
      pdfGenerator = TestPdfGenerator(),
    )

  @Test
  fun testCreatePdf() =
    runTest {
      val file =
        createPdf.invoke(
          category = HealthCareCategory.MEDICATIONS,
          listItemGroups =
            listOf(
              HealthCategoryScreenListItemsGroup(
                heading = CopyR.string.app_name,
                items =
                  listOf(
                    HealthCategoryScreenListItem(
                      title = "title",
                      subtitle = "subtitle",
                      mgoResource = TEST_MGO_RESOURCE,
                      organization = TEST_MGO_ORGANIZATION,
                    ),
                  ),
              ),
            ),
        )
      assertEquals("mgo_medicijnen_1_jan_2000.pdf", file.name)
    }
}
