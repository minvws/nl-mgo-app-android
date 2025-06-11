package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf

import nl.rijksoverheid.mgo.component.pdfViewer.Pdf
import nl.rijksoverheid.mgo.component.pdfViewer.PdfGenerator
import java.io.File

class TestPdfGenerator : PdfGenerator {
  override suspend fun invoke(
    pdf: Pdf,
    fileName: String,
  ): File = File(fileName)
}
