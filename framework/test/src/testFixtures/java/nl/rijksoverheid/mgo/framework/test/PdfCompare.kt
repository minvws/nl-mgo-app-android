package nl.rijksoverheid.mgo.framework.test

import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfReader
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor
import java.io.File

/**
 * Use to extract text from a pdf to compare pdfs in tests.
 * TODO Better would be to create bitmaps so that the design gets tested as well.
 */
fun extractPdfText(file: File): String {
  val pdfDoc = PdfDocument(PdfReader(file))
  val text = StringBuilder()
  for (i in 1..pdfDoc.numberOfPages) {
    text.append(PdfTextExtractor.getTextFromPage(pdfDoc.getPage(i)))
  }
  pdfDoc.close()
  return text.toString()
}
