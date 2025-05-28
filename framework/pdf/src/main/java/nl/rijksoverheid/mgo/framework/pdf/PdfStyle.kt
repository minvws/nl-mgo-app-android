package nl.rijksoverheid.mgo.framework.pdf

import android.graphics.Color

/**
 * Defines visual styling options for rendering a PDF document in [PdfGenerator].
 *
 * @param tableHeadingsBackgroundColor Background color for table header cells (default: transparent).
 * @param tableCellBorderColor Color used for the borders of all table cells (default: black).
 */
data class PdfStyle(
  val tableHeadingsBackgroundColor: Int = Color.TRANSPARENT,
  val tableCellBorderColor: Int = Color.BLACK,
  val footerTextColor: Int = Color.BLACK,
)
