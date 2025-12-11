package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf

import androidx.test.core.app.ApplicationProvider
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaSectionMapper
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.TEST_MGO_RESOURCE
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_PROBLEMS
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenListItem
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenListItemsGroup
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

@RunWith(RobolectricTestRunner::class)
internal class TestDefaultCreatePdfForHealthCategories {
  private val uiSchemaParser = mockk<UiSchemaParser>()
  private val createPdf =
    DefaultCreatePdfForHealthCategories(
      context = ApplicationProvider.getApplicationContext(),
      clock = Clock.fixed(Instant.parse("2000-01-01T10:01:00.00Z"), ZoneOffset.UTC),
      uiSchemaParser = uiSchemaParser,
      uiSchemaSectionMapper = UISchemaSectionMapper(MgoResourceStore()),
      pdfGenerator = TestPdfGenerator(),
    )

  @Before
  fun setup() {
    coEvery { uiSchemaParser.getSummary(any(), any()) } answers { HealthUiSchema(children = listOf(), label = "") }
  }

  @Test
  fun testCreatePdf() =
    runTest {
      val file =
        createPdf.invoke(
          category = TEST_HEALTH_CATEGORY_PROBLEMS,
          listItemGroups =
            listOf(
              HealthCategoryScreenListItemsGroup(
                heading = "Gezondheidsproblemen",
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
      assertEquals("mgo_medische_klachten_1_jan_2000.pdf", file.name)
    }
}
