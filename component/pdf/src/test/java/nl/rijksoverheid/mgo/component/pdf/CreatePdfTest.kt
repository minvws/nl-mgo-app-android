package nl.rijksoverheid.mgo.component.pdf

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.File

@Config(qualifiers = "nl-rNL", sdk = [34])
@RunWith(RobolectricTestRunner::class)
class CreatePdfTest {
  private val context = ApplicationProvider.getApplicationContext<Context>()
  private val pdfStore = MgoPdfStore(context)
  private val createPdf = CreatePdf(context = context, store = pdfStore)

  @Test
  fun test() {
    // Given: UI Schema
    val pdf =
      MgoPdf(
        fileName = "test.pdf",
        heading = "Heading",
        subheading = "Subheading",
        tables =
          listOf(
            MgoPdf.Tables(
              heading = "Heading",
              tables =
                listOf(
                  MgoPdf.Table(
                    sections =
                      listOf(
                        MgoPdf.Section(
                          heading = "Heading",
                          rows =
                            listOf(
                              MgoPdf.Row(
                                label = "Label",
                                content = listOf("Content"),
                              ),
                            ),
                        ),
                      ),
                  ),
                ),
            ),
          ),
      )

    // When: Creating PDF
    val outputPdfFile = createPdf.invoke(pdf)

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
