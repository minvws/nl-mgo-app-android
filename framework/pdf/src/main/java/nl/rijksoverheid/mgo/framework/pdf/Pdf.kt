package nl.rijksoverheid.mgo.framework.pdf

/**
 * Holds data required for generating a PDF using [PdfGenerator].
 *
 * @param heading The main heading displayed at the top of the PDF.
 * @param subHeading The subheading shown directly below the heading.
 * @param tables A list of tables to include in the PDF content.
 * @param footer The footer text displayed at the bottom of the PDF.
 */
data class Pdf(
  val heading: String,
  val subHeading: String,
  val tables: List<PdfTable>,
  val footer: String,
)

/**
 * Represents a table within a PDF document.
 *
 * @param heading A heading to display just above the table.
 * @param headers A list of column headers. The number of headers should match the number of columns.
 * @param columns The data columns to be displayed in the table.
 */
data class PdfTable(
  val heading: String,
  val headers: List<String>,
  val data: List<Pair<String, String>>,
)

/**
 * Represents a column in a PDF table.
 *
 * @param rows The list of row values in this column.
 */
data class PdfTableInner(
  val title: String,
  val data: List<Pair<String, String>>,
)
