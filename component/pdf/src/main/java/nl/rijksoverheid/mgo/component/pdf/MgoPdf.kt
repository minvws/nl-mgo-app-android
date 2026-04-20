package nl.rijksoverheid.mgo.component.pdf

import android.content.Context
import android.graphics.Bitmap
import androidx.annotation.DrawableRes
import androidx.compose.ui.graphics.Color
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import nl.rijksoverheid.mgo.component.theme.Black
import nl.rijksoverheid.mgo.component.theme.Gray600
import nl.rijksoverheid.mgo.component.theme.LogoBlue500
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DownloadBinary
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DownloadLink
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MultipleGroupedValues
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MultipleValues
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ReferenceLink
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ReferenceValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.SingleValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.UiElement
import java.io.ByteArrayOutputStream
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

data class MgoPdf(
  val fileName: MgoPdfFileName,
  val heading: String,
  val subheading: String,
  val tables: List<Tables>,
) {
  data class Tables(
    val heading: String?,
    val tables: List<Table>,
  )

  data class Table(
    val sections: List<Section>,
  )

  data class Section(
    val heading: String?,
    val rows: List<Row>,
  )

  data class Row(
    val label: String?,
    val content: List<String>,
    val labelColor: Color = Gray600,
    val contentColor: Color = Black,
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

fun UiElement.toRow(context: Context): MgoPdf.Row {
  val emptyText = context.getString(CopyR.string.common_unknown)
  return when (this) {
    is DownloadBinary -> {
      MgoPdf.Row(label = label, content = listOf(), labelColor = LogoBlue500, labelIcon = getAttachmentIconBytes(context))
    }

    is DownloadLink -> {
      MgoPdf.Row(label = label, content = listOf(), labelColor = LogoBlue500, labelIcon = getAttachmentIconBytes(context))
    }

    is MultipleGroupedValues -> {
      val content = value?.flatMap { value -> value.map { display -> display.display ?: emptyText } } ?: listOf(emptyText)
      MgoPdf.Row(
        label = label,
        content = content,
        contentColor = if (content.contains(emptyText)) Gray600 else Black,
      )
    }

    is MultipleValues -> {
      val content = value?.map { display -> display.display ?: emptyText } ?: listOf(emptyText)
      MgoPdf.Row(label = label, content = content, contentColor = if (content.contains(emptyText)) Gray600 else Black)
    }

    is ReferenceLink -> {
      MgoPdf.Row(label = label, content = listOf())
    }

    is ReferenceValue -> {
      val content = listOf(reference ?: emptyText)
      MgoPdf.Row(label = label, content = content, contentColor = if (content.contains(emptyText)) Gray600 else Black)
    }

    is SingleValue -> {
      val content = listOf(value?.display ?: emptyText)
      MgoPdf.Row(label = label, content = content, contentColor = if (content.contains(emptyText)) Gray600 else Black)
    }
  }
}

private fun getAttachmentIconBytes(context: Context): ByteArray? {
  val drawable = ContextCompat.getDrawable(context, R.drawable.ic_attachment)
  return drawable?.toBitmap()?.let { bitmap ->
    ByteArrayOutputStream().use { stream ->
      bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
      stream.toByteArray()
    }
  }
}
