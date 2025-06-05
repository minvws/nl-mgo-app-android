package nl.rijksoverheid.mgo.framework.pdf

data class Pdf(
  val heading: String,
  val subHeading: String,
  val groupedTables: List<PdfGroupedTables>,
  val footer: String,
)

data class PdfGroupedTables(
  val heading: String,
  val tables: List<PdfTable>,
)

data class PdfTable(
  val heading: String,
  val subTables: List<PdfSubTable>,
)

data class PdfSubTable(
  val heading: String?,
  val data: List<Pair<String, String>>,
)
