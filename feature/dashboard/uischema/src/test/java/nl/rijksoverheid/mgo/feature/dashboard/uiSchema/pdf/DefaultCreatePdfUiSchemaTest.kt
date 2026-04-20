package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.pdf

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.pdf.CreatePdf
import nl.rijksoverheid.mgo.component.pdf.MgoPdfStore
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DisplayValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DownloadBinary
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DownloadLink
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiGroup
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MultipleGroupedValues
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MultipleValues
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ReferenceLink
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ReferenceValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.SingleValue
import nl.rijksoverheid.mgo.framework.test.extractPdfText
import nl.rijksoverheid.mgo.framework.test.writeToHost
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File
import java.time.Clock
import java.time.Instant
import java.time.ZoneOffset

@Config(qualifiers = "nl-rNL", sdk = [34])
@RunWith(RobolectricTestRunner::class)
class DefaultCreatePdfUiSchemaTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val clock = Clock.fixed(Instant.parse("2026-04-20T14:46:00.00Z"), ZoneOffset.UTC)
  private val createPdf = CreatePdf(context = context, store = MgoPdfStore(context))
  private val createPdfUiSchema = DefaultCreatePdfUiSchema(context = context, clock = clock, createPdf = createPdf)

  @Test
  fun testCreatePdf() =
    runTest {
      // Given: UI Schemas
      val uiSchema =
        HealthUiSchema(
          label = "Label",
          children =
            listOf(
              HealthUiGroup(
                children =
                  listOf(
                    SingleValue(id = "1", label = "Single Value", value = DisplayValue(display = "Value")),
                    SingleValue(id = "1", label = "Single Value Empty", value = DisplayValue(display = null)),
                    MultipleValues(
                      id = "1",
                      label = "Multiple Value",
                      value = listOf(DisplayValue(display = "Value 1"), DisplayValue(display = "Value 2")),
                    ),
                    MultipleValues(id = "1", label = "Multiple Value Empty", value = null),
                    MultipleGroupedValues(
                      id = "1",
                      label = "Multiple Grouped Values",
                      value =
                        listOf(
                          listOf(
                            DisplayValue(display = "Value 1"),
                            DisplayValue(display = "Value 2"),
                          ),
                          listOf(DisplayValue(display = "Value 3"), DisplayValue(display = "Value 4")),
                        ),
                    ),
                    MultipleGroupedValues(
                      id = "1",
                      label = "Multiple Grouped Values Empty",
                      value = null,
                    ),
                    ReferenceLink(
                      id = "1",
                      reference = "",
                      label = "Reference Link",
                    ),
                    ReferenceValue(
                      id = "1",
                      label = "Reference Value",
                      reference = "Reference",
                    ),
                    ReferenceValue(
                      id = "1",
                      label = "Reference Value Empty",
                      reference = null,
                    ),
                  ),
              ),
              HealthUiGroup(
                label = "Downloads",
                children =
                  listOf(
                    DownloadBinary(
                      id = "1",
                      label = "Download Binary",
                      reference = null,
                    ),
                    DownloadLink(
                      id = "1",
                      label = "Download Link",
                    ),
                  ),
              ),
            ),
        )

      // When: Creating PDF
      val outputPdfFile = createPdfUiSchema(uiSchema = uiSchema)
      println(outputPdfFile.writeToHost().absolutePath)

      // Then: Created PDF is the same as test.pdf
      val testPdfFile =
        javaClass.classLoader!!
          .getResource("test.pdf")
          .toURI()
          .let { File(it) }

      assertEquals(extractPdfText(testPdfFile), extractPdfText(outputPdfFile))
    }
}
