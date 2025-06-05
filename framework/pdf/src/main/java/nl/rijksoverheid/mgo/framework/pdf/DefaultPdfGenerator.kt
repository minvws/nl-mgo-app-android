package nl.rijksoverheid.mgo.framework.pdf

import android.content.Context
import android.graphics.Color
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.canvas.PdfCanvas
import com.itextpdf.layout.Canvas
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.SolidBorder
import com.itextpdf.layout.element.AreaBreak
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.AreaBreakType
import com.itextpdf.layout.properties.TextAlignment
import com.itextpdf.layout.properties.VerticalAlignment
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

private const val PAGE_VERTICAL_MARGIN: Float = 32f
private const val PAGE_HORIZONTAL_MARGIN = 40f

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
     * @return The generated PDF file.
     */
    override suspend fun invoke(
      pdf: Pdf,
      style: PdfStyle,
      fileName: String,
    ): File {
      // Initialize the PDF writer and document with A4 landscape orientation.
      val file = File(context.cacheDir, fileName)
      val pdfWriter = PdfWriter(file)
      val pdfDoc = PdfDocument(pdfWriter)
      val document = Document(pdfDoc, PageSize.A4, false)

      // Set document margins (top, right, bottom, left).
      // Add more padding to the bottom to account for the footer.
      document.setMargins(PAGE_VERTICAL_MARGIN, PAGE_HORIZONTAL_MARGIN, PAGE_VERTICAL_MARGIN + 32f, PAGE_HORIZONTAL_MARGIN)

      // Add heading on top of the pdf
      val heading =
        Paragraph(pdf.heading)
          .setFontSize(24f)
          .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
      document.add(heading)

//      val subHeading =
//        Paragraph(pdf.subHeading)
//          .setFontSize(14f)
//          .setMarginTop(2f)
//      document.add(subHeading)

      // Create a table with dynamic column count based on the number of rows in the first column.
      val pageWidth = PageSize.A4.width
      val numColumns = 2

      val columnWidths = FloatArray(numColumns) { pageWidth / numColumns }

      for (groupedTableData in pdf.groupedTables) {
        // Add heading above the table using bold Helvetica font.
        val tableHeading =
          Paragraph(groupedTableData.heading)
            .setFontSize(16f)
            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
            .setMarginTop(16f)
        document.add(tableHeading)

        for (tableData in groupedTableData.tables) {
          val table = Table(columnWidths).setMarginTop(16f)

          table.addCell(
            Cell(1, 2)
              .add(Paragraph(tableData.heading).setFontSize(12f))
              .setBorder(SolidBorder(style.tableCellBorderColor.toDeviceRgb(), 1f))
              .setPadding(6f)
              .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
              .setTextAlignment(TextAlignment.CENTER)
              .setVerticalAlignment(VerticalAlignment.MIDDLE),
          )

          for (subTableData in tableData.subTables) {
            if (subTableData.heading != null) {
              table.addCell(
                Cell(1, 2)
                  .add(Paragraph(subTableData.heading).setFontSize(10f))
                  .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                  .setPadding(6f)
                  .setBorder(SolidBorder(style.tableCellBorderColor.toDeviceRgb(), 1f)),
              )
            }

            for ((col1, col2) in subTableData.data) {
              table.addCell(
                Cell()
                  .add(Paragraph(col1).setFontSize(10f))
                  .setBackgroundColor(style.tableHeadingsBackgroundColor.toDeviceRgb())
                  .setPadding(8f)
                  .setBorder(SolidBorder(style.tableCellBorderColor.toDeviceRgb(), 1f)),
              )
              table.addCell(
                Cell()
                  .add(Paragraph(col2).setFontSize(10f))
                  .setPadding(8f)
                  .setBorder(SolidBorder(style.tableCellBorderColor.toDeviceRgb(), 1f)),
              )
            }
          }

          // Add table
          document.add(table)
        }

        // Next groups are always on a new page
        val lastPage = pdf.groupedTables.indexOf(groupedTableData) == pdf.groupedTables.lastIndex
        if (!lastPage) {
          document.add(AreaBreak(AreaBreakType.NEXT_PAGE))
        }
      }

      // Add footer to each page
      val numberOfPages = pdfDoc.numberOfPages
      for (i in 1..numberOfPages) {
        val page = pdfDoc.getPage(i)
        val canvas = PdfCanvas(page)
        val pageSize = pdfDoc.firstPage.pageSize
        val layoutCanvas = Canvas(canvas, pageSize)

        // Add footer text
//        layoutCanvas.showTextAligned(
//          Paragraph(pdf.footer).setFontSize(10f).setFontColor(style.footerTextColor.toDeviceRgb()),
//          PAGE_HORIZONTAL_MARGIN,
//          PAGE_VERTICAL_MARGIN,
//          TextAlignment.LEFT,
//        )

        // Add page number
        val pageFooterText = context.resources.getString(CopyR.string.export_pdf_page, i, numberOfPages)
        layoutCanvas.showTextAligned(
          Paragraph(pageFooterText).setFontSize(10f).setFontColor(style.footerTextColor.toDeviceRgb()),
          page.pageSize.width - PAGE_HORIZONTAL_MARGIN,
          PAGE_VERTICAL_MARGIN,
          TextAlignment.RIGHT,
        )
      }

      document.close()
      return file
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
