package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor
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
import nl.rijksoverheid.mgo.data.healthCategories.models.TEST_HEALTH_CATEGORY_MEDICATION
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
import kotlin.intArrayOf

@Config(qualifiers = "nl-rNL", sdk = [34])
@RunWith(RobolectricTestRunner::class)
class DefaultCreatePdfHealthCategoryTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val clock = Clock.fixed(Instant.parse("2000-01-01T10:01:00.00Z"), ZoneOffset.UTC)
  private val createPdf = CreatePdf(context = context, store = MgoPdfStore(context))
  private val createPdfHealthCategory = DefaultCreatePdfHealthCategory(context = context, clock = clock, createPdf = createPdf)

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
                    DownloadBinary(
                      id = "1",
                      label = "Download Binary",
                      reference = null,
                    ),
                    DownloadLink(
                      id = "1",
                      label = "Download Link",
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
            ),
        )
      val groupedUiSchemas =
        listOf(
          GroupedHealthUiSchemas(heading = "Heading", uiSchemas = listOf(uiSchema, uiSchema)),
          GroupedHealthUiSchemas(heading = "Heading", uiSchemas = listOf(uiSchema, uiSchema)),
        )

      // When: Creating PDF
      val outputPdfFile = createPdfHealthCategory(uiSchemas = groupedUiSchemas, category = TEST_HEALTH_CATEGORY_MEDICATION)
      println(outputPdfFile.writeToHost().absolutePath)

      // Then: Created PDF is the same as test.pdf
      val testPdfFile =
        javaClass.classLoader!!
          .getResource("test.pdf")
          .toURI()
          .let { File(it) }

      assertEquals(extractPdfText(testPdfFile), extractPdfText(outputPdfFile))
    }

  private fun extractPdfText(file: File): String {
    val pdfDoc = PdfDocument(PdfReader(file))
    val text = StringBuilder()
    for (i in 1..pdfDoc.numberOfPages) {
      text.append(PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i)))
    }
    pdfDoc.close()
    return text.toString()
  }
}
