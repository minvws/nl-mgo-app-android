package nl.rijksoverheid.mgo.component.pdfViewer

import java.io.File

/**
 * Generates a PDF file.
 */
interface PdfGenerator {
  /**
   * Generates a PDF file with the specified content.
   *
   * @param pdf The content to be displayed in the PDF.
   * @param fileName The name of the output PDF file, including the extension (e.g., "file.pdf").
   * @return The generated PDF file.
   */
  suspend operator fun invoke(
    pdf: Pdf,
    fileName: String,
  ): File
}
