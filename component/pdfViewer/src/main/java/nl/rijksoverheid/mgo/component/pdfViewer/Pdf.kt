package nl.rijksoverheid.mgo.component.pdfViewer

/**
 * Represents the complete content structure of a PDF document.
 *
 * @property heading The main title displayed at the top of the PDF.
 * @property subHeading A subtitle providing additional context or description.
 * @property groupedTables A list of grouped tables, each with its own heading and associated tables.
 * @property footer A footer text displayed at the bottom of the PDF.
 */
data class Pdf(
  val heading: String,
  val subHeading: String,
  val groupedTables: List<PdfGroupedTables>,
  val footer: String,
)

/**
 * Represents a logical grouping of tables under a common heading within the PDF.
 *
 * @property heading The title for this group of tables.
 * @property tables A list of individual tables included in this group.
 */
data class PdfGroupedTables(
  val heading: String,
  val tables: List<PdfTable>,
)

/**
 * Represents a single table in the PDF, possibly composed of multiple subtables.
 *
 * @property heading The title of the table.
 * @property subTables A list of subtables that make up the full table structure.
 */
data class PdfTable(
  val heading: String,
  val subTables: List<PdfSubTable>,
)

/**
 * Represents a sub-section of a table, used to break down complex data.
 *
 * @property heading An optional title for the subtable. Can be null if not applicable.
 * @property data A list of key-value pairs representing the subtable’s content rows.
 *                Each pair corresponds to a label (left column) and its value (right column).
 */
data class PdfSubTable(
  val heading: String?,
  val data: List<Pair<String, String>>,
)
