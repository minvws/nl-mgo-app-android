package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.component.pdf.CreatePdf
import nl.rijksoverheid.mgo.data.hcimParser.UiSchemaParserFactory
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.TEST_ZIB_PROBLEM
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.createMgoResource
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.Profiles
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_PROBLEMS
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

@Config(sdk = [34])
@RunWith(RobolectricTestRunner::class)
internal class DefaultCreatePdfHealthCategoryTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val clock = Clock.fixed(Instant.parse("2000-01-01T10:01:00.00Z"), ZoneOffset.UTC)
  private val createPdf = mockk<CreatePdf>(relaxed = true)
  private lateinit var uiSchemaParser: UiSchemaParser
  private lateinit var usecase: DefaultCreatePdfHealthCategory

  @Before
  fun setup() =
    runTest {
      uiSchemaParser = UiSchemaParserFactory.createForJvm()
      usecase =
        DefaultCreatePdfHealthCategory(
          context = context,
          clock = clock,
          createPdf = createPdf,
          uiSchemaParser = uiSchemaParser,
        )
    }

  @Test
  fun testInvoke() =
    runTest {
      // Given: Health category
      val category = TEST_HEALTH_CATEGORY_PROBLEMS

      // Given: Mgo resource
      val mgoResource =
        createMgoResource(
          organizationId = TEST_MGO_ORGANIZATION.id,
          profile = Profiles.zibProblem,
          decodedObject = TEST_ZIB_PROBLEM(),
        )

      // When: Calling invoke
      usecase.invoke(mgoResources = listOf(mgoResource), category = category)

      // Then: Pdf is created
      verify(exactly = 1) {
        createPdf(
          match {
            it.fileName == "mgo_medische_klachten_1_jan_2000.pdf" &&
              it.heading == "Medische klachten" &&
              it.subheading.contains("10:01") &&
              it.subheading.contains("AM")
          },
        )
      }
    }
}
