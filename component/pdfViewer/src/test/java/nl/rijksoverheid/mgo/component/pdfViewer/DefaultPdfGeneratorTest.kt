package nl.rijksoverheid.mgo.component.pdfViewer

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

@Config(qualifiers = "nl-rNL")
@RunWith(RobolectricTestRunner::class)
class DefaultPdfGeneratorTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val pdfFileRepository = PdfFileRepository(context)
  private val generator =
    DefaultPdfGenerator(context = context, pdfFileRepository = pdfFileRepository)

  @Test
  fun testGeneratePdf() =
    runTest {
      val pdf =
        Pdf(
          heading = "Heading",
          subHeading = "Subheading",
          groupedTables =
            listOf(
              PdfGroupedTables(
                heading = "Grouped Table #1",
                tables =
                  listOf(
                    PdfTable(
                      heading = "Table Heading #1",
                      subTables =
                        listOf(
                          PdfSubTable(
                            heading = "Table Sub Heading #1",
                            data =
                              listOf(
                                Pair("Data 1", "Data 2"),
                                Pair("Data 3", "Data 4"),
                                Pair("Data 5", "Data 6"),
                              ),
                          ),
                        ),
                    ),
                  ),
              ),
              PdfGroupedTables(
                heading = "Grouped Table #2",
                tables = listOf(),
              ),
              PdfGroupedTables(
                heading = "Grouped Table #3",
                tables =
                  listOf(
                    PdfTable(
                      heading = "Table Heading #1",
                      subTables =
                        listOf(
                          PdfSubTable(
                            heading = "Table Sub Heading #1",
                            data =
                              listOf(
                                Pair("Data 1", "Data 2"),
                                Pair("Data 3", "Data 4"),
                                Pair("Data 5", "Data 6"),
                              ),
                          ),
                        ),
                    ),
                    PdfTable(
                      heading = "Table Heading #2",
                      subTables =
                        listOf(
                          PdfSubTable(
                            heading = "Table Sub Heading #2",
                            data =
                              listOf(
                                Pair("Data 1", "Data 2"),
                                Pair("Data 3", "Data 4"),
                                Pair("Data 5", "Data 6"),
                              ),
                          ),
                        ),
                    ),
                  ),
              ),
            ),
          footer = "Footer",
        )

      // When: generating pdf
      generator.invoke(pdf = pdf, fileName = "test.pdf")

      // Then: expected pdf is generated
      val createdPdfFile = pdfFileRepository.get("test.pdf")
      val expectedPdfFile =
        javaClass.classLoader!!
          .getResource("test.pdf")
          .toURI()
          .let { File(it) }
      assertEquals(extractPdfText(expectedPdfFile), extractPdfText(createdPdfFile))
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
