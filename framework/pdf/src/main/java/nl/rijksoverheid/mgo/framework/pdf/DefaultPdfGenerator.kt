package nl.rijksoverheid.mgo.framework.pdf

import android.content.Context
import com.itextpdf.kernel.colors.ColorConstants
import com.itextpdf.kernel.geom.PageSize
import com.itextpdf.kernel.pdf.PdfDocument
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.File
import javax.inject.Inject

internal class DefaultPdfGenerator
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
  ) : PdfGenerator {
    override suspend fun invoke() {
      val numColumns = 5
      val pdfWriter = PdfWriter(File(context.cacheDir, "sample.pdf"))
      val pdfDoc = PdfDocument(pdfWriter)
      val document = Document(pdfDoc, PageSize.A4)

      val margin = 36f
      document.setMargins(margin, margin, margin, margin)

      val pageWidth = PageSize.A4.width - 2 * margin
      val columnWidths = FloatArray(numColumns) { pageWidth / numColumns }

      val table = Table(columnWidths)

      // Add header cells with background color
      for (col in 1..numColumns) {
        val headerCell =
          Cell()
            .add(Paragraph("Header $col"))
            .setBackgroundColor(ColorConstants.LIGHT_GRAY)
            .setFontColor(ColorConstants.BLACK)
            .setPadding(8f)
        table.addHeaderCell(headerCell)
      }

      // Add some example data cells below headers
      for (row in 1..5) {
        for (col in 1..numColumns) {
          table.addCell(Cell().add(Paragraph("Row $row, Col $col")).setPadding(6f))
        }
      }

      document.add(table)
      document.close()
    }
  }
