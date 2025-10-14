package nl.rijksoverheid.mgo.component.pdfViewer

import android.content.Context
import android.graphics.Color
import androidx.core.graphics.toColorInt
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

private val HIGHLIGHTED_CELL_BACKGROUND_COLOR = "#F4F4F4".toColorInt().toDeviceRgb()
private val CELL_BORDER_COLOR = "#E1E1E1".toColorInt().toDeviceRgb()
private val SUBHEADING_TEXT_COLOR = "#6D6D6D".toColorInt().toDeviceRgb()
private val FOOTER_TEXT_COLOR = "#6D6D6D".toColorInt().toDeviceRgb()
private const val PAGE_MARGIN: Float = 28f

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
    private val pdfFileRepository: PdfFileRepository,
  ) : PdfGenerator {
    /**
     * Creates a styled PDF document based on provided content.
     *
     * @param pdf The PDF content model, including tables and headers.
     * @param fileName Desired filename for the PDF, including extension (e.g., "report.pdf").
     * @return The generated PDF file.
     */
    override suspend fun invoke(
      pdf: Pdf,
      fileName: String,
    ): File {
      // Initialize the PDF writer and document with A4 landscape orientation.
      val file = pdfFileRepository.get(fileName)
      val pdfWriter = PdfWriter(file)
      val pdfDoc = PdfDocument(pdfWriter)
      val document = Document(pdfDoc, PageSize.A4, false)

      // Set document margins (top, right, bottom, left).
      // Add more padding to the bottom to account for the footer.
      document.setMargins(PAGE_MARGIN, PAGE_MARGIN, PAGE_MARGIN + 32f, PAGE_MARGIN)

      // Add heading on top of the pdf
      val headingParagraph =
        Paragraph(pdf.heading)
          .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
          .setFontSize(24f)
      document.add(headingParagraph)

      val firstPage = pdfDoc.firstPage
      val firstPageCanvas = PdfCanvas(firstPage)
      val firstPageSize = firstPage.pageSize
      val firstPageLayoutCanvas = Canvas(firstPageCanvas, firstPageSize)

      // Add sub heading at the right top corner
      val subHeadingParagraph =
        Paragraph(pdf.subHeading)
          .setFontSize(10f)
          .setFontColor(SUBHEADING_TEXT_COLOR)

      firstPageLayoutCanvas.showTextAligned(
        subHeadingParagraph,
        firstPage.pageSize.width - PAGE_MARGIN,
        firstPage.pageSize.height - PAGE_MARGIN,
        TextAlignment.RIGHT,
      )

      for (groupedTableData in pdf.groupedTables) {
        // Add heading above the table.
        val tableHeadingParagraph =
          Paragraph(groupedTableData.heading)
            .setMarginTop(16f)
            .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
            .setFontSize(16f)
        document.add(tableHeadingParagraph)

        val hasNoData = groupedTableData.tables.all { it.subTables.isEmpty() }
        if (hasNoData) {
          // If we do not have any tables to show, show an empty state
          val noDataParagraph =
            Paragraph(context.getString(CopyR.string.export_pdf_no_data))
              .setMarginTop(16f)
              .setPadding(6f)
              .setFontSize(10f)
              .setBorder(SolidBorder(HIGHLIGHTED_CELL_BACKGROUND_COLOR, 1f))
          document.add(noDataParagraph)
        } else {
          // Create and show tables if we have data
          val pageWidth = PageSize.A4.width
          val numColumns = 2
          val columnWidths = FloatArray(numColumns) { pageWidth / numColumns }

          for (tableData in groupedTableData.tables) {
            val table =
              Table(columnWidths)
                .setMarginTop(16f)
                .setMarginBottom(16f)
                .setBorder(SolidBorder(CELL_BORDER_COLOR, 1f))

            // Add heading inside the table
            val tableCellHeadingParagraph =
              Paragraph(tableData.heading)
                .setPadding(6f)
                .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                .setFontSize(12f)
                .setTextAlignment(TextAlignment.CENTER)
                .setVerticalAlignment(VerticalAlignment.MIDDLE)
            val tableCellHeading =
              Cell(1, 2)
                .add(tableCellHeadingParagraph)
                .setBorder(SolidBorder(CELL_BORDER_COLOR, 1f))
            table.addCell(tableCellHeading)

            for (subTableData in tableData.subTables) {
              if (subTableData.heading != null) {
                // Add sub headings inside the table
                val tableCellSubHeadingParagraph =
                  Paragraph(subTableData.heading)
                    .setPadding(6f)
                    .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
                    .setFontSize(10f)
                val tableCellSubHeadingCell =
                  Cell(1, 2)
                    .add(tableCellSubHeadingParagraph)
                    .setBorder(SolidBorder(CELL_BORDER_COLOR, 1f))
                table.addCell(tableCellSubHeadingCell)
              }

              // Add cells
              for ((leftCellText, rightCellText) in subTableData.data) {
                val leftTableCellParagraph =
                  Paragraph(leftCellText)
                    .setPadding(6f)
                    .setFontSize(10f)
                val tableLeftCell =
                  Cell()
                    .add(leftTableCellParagraph)
                    .setBackgroundColor(HIGHLIGHTED_CELL_BACKGROUND_COLOR)
                    .setBorder(SolidBorder(CELL_BORDER_COLOR, 1f))
                table.addCell(tableLeftCell)

                val rightTableCellParagraph =
                  Paragraph(rightCellText)
                    .setPadding(6f)
                    .setFontSize(10f)
                val tableRightCell =
                  Cell().add(rightTableCellParagraph).setBorder(
                    SolidBorder(
                      CELL_BORDER_COLOR,
                      1f,
                    ),
                  )
                table.addCell(tableRightCell)
              }
            }

            // Add table
            document.add(table)
          }
        }

        // Next groups are always on a new page
        val lastPage = pdf.groupedTables.indexOf(groupedTableData) == pdf.groupedTables.lastIndex
        if (!lastPage) {
          document.add(AreaBreak(AreaBreakType.NEXT_PAGE))
        }
      }

      val numberOfPages = pdfDoc.numberOfPages
      for (i in 1..numberOfPages) {
        val page = pdfDoc.getPage(i)
        val pageCanvas = PdfCanvas(page)
        val pageSize = page.pageSize
        val pageLayoutCanvas = Canvas(pageCanvas, pageSize)

        // Add footer text to the bottom left of each page
        val footerParagraph =
          Paragraph(pdf.footer)
            .setFontSize(10f)
            .setFontColor(FOOTER_TEXT_COLOR)
        pageLayoutCanvas.showTextAligned(
          footerParagraph,
          PAGE_MARGIN,
          PAGE_MARGIN,
          TextAlignment.LEFT,
        )

        // Add page number text to the bottom right of each page
        val pageFooterText = context.resources.getString(CopyR.string.export_pdf_page, i, numberOfPages)
        val pageFooterParagraph =
          Paragraph(pageFooterText)
            .setFontSize(10f)
            .setFontColor(FOOTER_TEXT_COLOR)
        pageLayoutCanvas.showTextAligned(
          pageFooterParagraph,
          page.pageSize.width - PAGE_MARGIN,
          PAGE_MARGIN,
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
