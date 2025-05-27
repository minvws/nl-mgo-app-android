package nl.rijksoverheid.mgo.framework.pdf

/**
 * Generates a PDF file.
 */
interface PdfGenerator {
  /**
   * Generates a PDF file with the specified content and configuration.
   *
   * @param pdf The content to be displayed in the PDF.
   * @param style Styling and layout options for the PDF.
   * @param fileName The name of the output PDF file, including the extension (e.g., "file.pdf").
   */
  suspend operator fun invoke(
    pdf: Pdf,
    style: PdfStyle,
    fileName: String,
  )
}
