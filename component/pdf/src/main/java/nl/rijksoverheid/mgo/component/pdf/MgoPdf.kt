package nl.rijksoverheid.mgo.component.pdf

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import nl.rijksoverheid.mgo.component.theme.Gray500
import nl.rijksoverheid.mgo.component.theme.Gray600

data class MgoPdf(
  val fileName: String,
  val heading: String,
  val subheading: String,
  val tables: List<Tables>,
) {
  data class Tables(
    val heading: String,
    val tables: List<Table>,
  )

  data class Table(
    val sections: List<Section>,
  )

  data class Section(
    val heading: String,
    val rows: List<Row>,
  )

  data class Row(
    val label: String?,
    val content: List<String>,
    val labelColor: Color = Gray600,
    @field:DrawableRes val icon: Int? = null,
  )
}
