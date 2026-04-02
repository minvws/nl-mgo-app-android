package nl.rijksoverheid.mgo.component.pdf

import android.content.Context
import androidx.compose.ui.graphics.toArgb
import com.itextpdf.io.font.constants.StandardFonts
import com.itextpdf.kernel.colors.DeviceRgb
import com.itextpdf.kernel.font.PdfFontFactory
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfPage
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.kernel.pdf.canvas.PdfCanvas
import com.itextpdf.layout.Canvas
import com.itextpdf.layout.Document
import com.itextpdf.layout.borders.Border
import com.itextpdf.layout.borders.SolidBorder
import com.itextpdf.layout.element.AreaBreak
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.properties.AreaBreakType
import com.itextpdf.layout.properties.TextAlignment
import dagger.hilt.android.qualifiers.ApplicationContext
import nl.rijksoverheid.mgo.component.theme.Gray100
import nl.rijksoverheid.mgo.component.theme.Gray500
import nl.rijksoverheid.mgo.component.theme.Gray600
import java.io.File
import javax.inject.Inject
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

private object CreatePdfSettings {
  const val PAGE_SPACING: Float = 28f
  const val TABLE_SPACING: Float = 12f

  const val HEADING_TEXT_SIZE: Float = 24f

  const val SUBHEADING_TEXT_SIZE: Float = 10f

  const val TABLE_HEADING_TEXT_SIZE: Float = 16f

  const val SECTION_HEADING_FIRST_TEXT_SIZE: Float = 14f

  const val SECTION_HEADING_TEXT_SIZE: Float = 12f

  const val SECTION_TEXT_SIZE = 10f

  const val FOOTER_TEXT_SIZE = 10f
  val TABLE_CELL_COLOR = Gray100.toArgb().toDeviceRgb()
  val FOOTER_TEXT_COLOR = Gray600.toArgb().toDeviceRgb()
}

class CreatePdf
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
    private val store: MgoPdfStore,
  ) {
    operator fun invoke(pdf: MgoPdf): File {
      // Create pdf document
      val file = store.get(pdf.fileName)
      val pdfWriter = PdfWriter(file)
      val pdfDoc = PdfDocument(pdfWriter)
      val document = Document(pdfDoc, PageSize.A4, false)

      // Set document margins (top, right, bottom, left). Add more padding to the bottom to account for the footer.
      document.setMargins(
        CreatePdfSettings.PAGE_SPACING,
        CreatePdfSettings.PAGE_SPACING,
        CreatePdfSettings.PAGE_SPACING + 32f,
        CreatePdfSettings.PAGE_SPACING,
      )

      // Add heading at top of the pdf
      document.addHeading(pdf.heading)

      // Add sub heading at the top right of the pdf
      addSubheading(text = pdf.subheading, page = pdfDoc.firstPage)

      // Add tables
      pdf.tables.forEachIndexed { index, tables ->
        document.addTables(tables)
        if (index != tables.tables.lastIndex) {
          document.add(AreaBreak(AreaBreakType.NEXT_PAGE))
        }
      }

      // Add footer text and page numbers
      for (i in 1..pdfDoc.numberOfPages) {
        val page = pdfDoc.getPage(i)
        val pageCanvas = PdfCanvas(page)
        val pageSize = page.pageSize
        val pageLayoutCanvas = Canvas(pageCanvas, pageSize)
        pageLayoutCanvas.addFooter()
        pageLayoutCanvas.addPageNumber(page = page, currentPage = i, numberOfPages = pdfDoc.numberOfPages)
      }

      // Return file
      document.close()
      return file
    }

    private fun Document.addHeading(text: String) {
      val headingParagraph =
        Paragraph(text)
          .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
          .setFontSize(CreatePdfSettings.HEADING_TEXT_SIZE)
      add(headingParagraph)
    }

    private fun addSubheading(
      text: String,
      page: PdfPage,
    ) {
      val subHeadingParagraph =
        Paragraph(text)
          .setFontSize(CreatePdfSettings.SUBHEADING_TEXT_SIZE)
          .setFontColor(Gray500.toArgb().toDeviceRgb())
      val firstPageCanvas = PdfCanvas(page)
      val firstPageSize = page.pageSize
      val firstPageLayoutCanvas = Canvas(firstPageCanvas, firstPageSize)
      firstPageLayoutCanvas.showTextAligned(
        subHeadingParagraph,
        page.pageSize.width - CreatePdfSettings.PAGE_SPACING,
        page.pageSize.height - CreatePdfSettings.PAGE_SPACING,
        TextAlignment.RIGHT,
      )
    }

    private fun Document.addTables(tables: MgoPdf.Tables) {
      val heading =
        Paragraph(tables.heading)
          .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
          .setFontSize(CreatePdfSettings.TABLE_HEADING_TEXT_SIZE)
          .setPadding(0f)
      add(heading)

      for (table in tables.tables) {
        addTable(table)
      }
    }

    private fun Document.addTable(mgoTable: MgoPdf.Table) {
      val pageWidth = PageSize.A4.width
      val numColumns = 2
      val columnWidths = FloatArray(numColumns) { pageWidth / numColumns }

      val table =
        Table(columnWidths)
          .setBorder(SolidBorder(CreatePdfSettings.TABLE_CELL_COLOR, 1f))
          .setMargins(CreatePdfSettings.TABLE_SPACING, 0f, CreatePdfSettings.TABLE_SPACING, 0f)
          .setKeepTogether(true)

      mgoTable.sections.forEachIndexed { index, section ->
        val headingSize = if (index == 0) CreatePdfSettings.SECTION_HEADING_FIRST_TEXT_SIZE else CreatePdfSettings.SECTION_HEADING_TEXT_SIZE
        val headingPaddingTop = if (index == 0) CreatePdfSettings.TABLE_SPACING else 0f
        val headingPaddingBottom = if (index == 0) 4f else 0f
        table.addTableSection(section = section, headingSize = headingSize, headingPaddingTop = headingPaddingTop, headingPaddingBottom = headingPaddingBottom)
      }

      add(table)
    }

    private fun Table.addTableSection(
      section: MgoPdf.Section,
      headingSize: Float,
      headingPaddingTop: Float,
      headingPaddingBottom: Float,
    ) {
      // Add heading
      val heading =
        Paragraph(section.heading)
          .setFont(PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD))
          .setFontSize(headingSize)

      val headingCell =
        Cell(1, 2)
          .add(heading)
          .setPaddings(headingPaddingTop, CreatePdfSettings.TABLE_SPACING, headingPaddingBottom, CreatePdfSettings.TABLE_SPACING)
          .setBorder(Border.NO_BORDER)
      addCell(headingCell)

      // Add rows
      section.rows.forEachIndexed { index, row ->
        val paddingBottom = if (index == section.rows.lastIndex) CreatePdfSettings.TABLE_SPACING else 0f
        val labelHeading =
          Paragraph(
            row.label,
          ).setFontSize(
            CreatePdfSettings.SECTION_TEXT_SIZE,
          ).setFontColor(row.labelColor.toArgb().toDeviceRgb())
            .setPaddings(4f, CreatePdfSettings.TABLE_SPACING, paddingBottom, CreatePdfSettings.TABLE_SPACING)
        val labelCell = Cell().add(labelHeading).setPadding(0f).setBorder(Border.NO_BORDER)
        addCell(labelCell)

        if (row.content.isEmpty()) {
          val contentCell = Cell().setBorder(Border.NO_BORDER)
          addCell(contentCell)
        } else {
          val contentHeading =
            Paragraph(
              row.content.first(),
            ).setFontSize(
              CreatePdfSettings.SECTION_TEXT_SIZE,
            ).setPaddings(4f, CreatePdfSettings.TABLE_SPACING, paddingBottom, CreatePdfSettings.TABLE_SPACING)
          val contentCell = Cell().add(contentHeading).setPadding(0f).setBorder(Border.NO_BORDER)
          addCell(contentCell)
        }
      }
    }

    private fun Canvas.addFooter() {
      val text = context.resources.getString(CopyR.string.export_pdf_footer)
      val footerParagraph =
        Paragraph(text)
          .setFontSize(CreatePdfSettings.FOOTER_TEXT_SIZE)
          .setFontColor(CreatePdfSettings.FOOTER_TEXT_COLOR)
      showTextAligned(
        footerParagraph,
        CreatePdfSettings.PAGE_SPACING,
        CreatePdfSettings.PAGE_SPACING,
        TextAlignment.LEFT,
      )
    }

    private fun Canvas.addPageNumber(
      page: PdfPage,
      currentPage: Int,
      numberOfPages: Int,
    ) {
      val text = context.resources.getString(CopyR.string.export_pdf_page, currentPage, numberOfPages)
      val pageFooterParagraph =
        Paragraph(text)
          .setFontSize(CreatePdfSettings.FOOTER_TEXT_SIZE)
          .setFontColor(CreatePdfSettings.FOOTER_TEXT_COLOR)
      showTextAligned(
        pageFooterParagraph,
        page.pageSize.width - CreatePdfSettings.PAGE_SPACING,
        CreatePdfSettings.PAGE_SPACING,
        TextAlignment.RIGHT,
      )
    }
  }

private fun Int.toDeviceRgb(): DeviceRgb {
  val r = (this shr 16) and 0xFF
  val g = (this shr 8) and 0xFF
  val b = this and 0xFF
  return DeviceRgb(r, g, b)
}
