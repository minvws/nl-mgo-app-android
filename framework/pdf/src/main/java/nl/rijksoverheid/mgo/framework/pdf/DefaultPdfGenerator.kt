package nl.rijksoverheid.mgo.framework.pdf

import android.content.Context
import android.graphics.Color
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.SolidBorder
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.VerticalAlignment
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

/**
 * Implementation of [PdfGenerator] that generates and stores a PDF file
 * in the app's cache directory using the iText library.
 *
 * @param context Application context for accessing the file system.
 */
internal class DefaultPdfGenerator
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
  ) : PdfGenerator {
    /**
     * Creates a styled PDF document based on provided content and saves it to cache.
     *
     * @param pdf The PDF content model, including tables and headers.
     * @param style Visual styling options such as border and background colors.
     * @param fileName Desired filename for the PDF, including extension (e.g., "report.pdf").
     */
    override suspend fun invoke(
      pdf: Pdf,
      style: PdfStyle,
      fileName: String,
    ) {
      // Initialize the PDF writer and document with A4 landscape orientation.
      val pdfWriter = PdfWriter(File(context.cacheDir, fileName))
      val pdfDoc = PdfDocument(pdfWriter)
      val document = Document(pdfDoc, PageSize.A4.rotate())

      // Set document margins (top, right, bottom, left).
      document.setMargins(32f, 40f, 32f, 40f)

      // Add heading above the table using bold Helvetica font.
      val tableHeading =
        Paragraph(pdf.tables[0].heading)
          .setFontSize(16f)
          .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
          .setPaddings(0f, 0f, 8f, 0f)
      document.add(tableHeading)

      // Create a table with dynamic column count based on the number of rows in the first column.
      val pageWidth = PageSize.A4.width
      val numColumns =
        pdf.tables[0]
          .columns[0]
          .rows.size
      val columnWidths = FloatArray(numColumns) { pageWidth / numColumns }
      val table = Table(columnWidths)

      // Add table headers with background color and borders.
      for (header in pdf.tables[0].headers) {
        val headerCell =
          Cell()
            .add(Paragraph(header).setFontSize(10f))
            .setBackgroundColor(style.tableHeadingsBackgroundColor.toDeviceRgb())
            .setPadding(8f)
            .setBorder(SolidBorder(style.tableCellBorderColor.toDeviceRgb(), 1f))
            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
            .setVerticalAlignment(VerticalAlignment.MIDDLE)
        table.addHeaderCell(headerCell)
      }

      // Populate the table with data rows from each column.
      for (column in pdf.tables[0].columns) {
        for (row in column.rows) {
          table.addCell(
            Cell()
              .add(Paragraph(row).setFontSize(10f))
              .setPadding(8f)
              .setBorder(SolidBorder(style.tableCellBorderColor.toDeviceRgb(), 1f)),
          )
        }
      }

      // Finalize and close the document.
      document.add(table)
      document.close()
    }
  }

/**
 * Converts an ARGB color Int to an iText DeviceRgb.
 */
private fun Int.toDeviceRgb(): DeviceRgb {
  val r = Color.red(this)
  val g = Color.green(this)
  val b = Color.blue(this)
  return DeviceRgb(r, g, b)
}
