package nl.rijksoverheid.mgo.component.pdf

import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import nl.rijksoverheid.mgo.component.theme.Gray600

data class MgoPdf(
  val fileName: MgoPdfFileName,
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
    @field:DrawableRes val labelIcon: ByteArray? = null,
  ) {
    override fun equals(other: Any?): Boolean {
      if (this === other) return true
      if (javaClass != other?.javaClass) return false

      other as Row

      if (label != other.label) return false
      if (content != other.content) return false
      if (labelColor != other.labelColor) return false
      if (!labelIcon.contentEquals(other.labelIcon)) return false

      return true
    }

    override fun hashCode(): Int {
      var result = label?.hashCode() ?: 0
      result = 31 * result + content.hashCode()
      result = 31 * result + labelColor.hashCode()
      result = 31 * result + (labelIcon?.contentHashCode() ?: 0)
      return result
    }
  }
}
