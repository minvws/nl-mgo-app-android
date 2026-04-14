package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf

import android.content.Context
import android.graphics.Bitmap
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.toBitmap
import dagger.hilt.android.qualifiers.ApplicationContext
import getString
import nl.rijksoverheid.mgo.component.pdf.CreatePdf
import nl.rijksoverheid.mgo.component.pdf.MgoPdf
import nl.rijksoverheid.mgo.component.theme.LogoBlue500
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DownloadBinary
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DownloadLink
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiGroup
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MultipleGroupedValues
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MultipleValues
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ReferenceLink
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ReferenceValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.SingleValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.UiElement
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.R
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.groupBySubCategory
import java.io.ByteArrayOutputStream
import java.io.File
import java.time.Clock
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject
import javax.inject.Named
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

class DefaultCreatePdfHealthCategory
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
    @Named("systemDefaultZone") private val clock: Clock,
    private val createPdf: CreatePdf,
  ) : CreatePdfHealthCategory {
    override suspend operator fun invoke(
      uiSchemas: List<GroupedHealthUiSchemas>,
      category: HealthCategoryGroup.HealthCategory,
    ): File {
      val pdf = uiSchemas.toMgoPdf(category)
      return createPdf(pdf)
    }

    private suspend fun List<GroupedHealthUiSchemas>.toMgoPdf(category: HealthCategoryGroup.HealthCategory): MgoPdf {
      val heading = context.getString(category.heading)
      return MgoPdf(
        fileName = getFileName(heading),
        heading = heading,
        subheading = getSubheading(),
        tables = fromGroupedHealthUiSchemasToTable(),
      )
    }

    private fun getFileName(heading: String): String {
      val now = LocalDateTime.now(clock)
      return buildString {
        append("mgo")
        append("_")
        append(heading.lowercase().replace(" ", "_"))
        append("_")
        append(now.dayOfMonth)
        append("_")
        append(
          now.month
            .getDisplayName(TextStyle.SHORT, Locale.getDefault())
            .lowercase()
            .replace(".", ""),
        )
        append("_")
        append(now.year)
        append(".pdf")
      }
    }

    private fun getSubheading(): String {
      val deviceLocale = Locale.getDefault()
      val now = LocalDateTime.now(clock)
      val mediumDateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(deviceLocale)
      val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(deviceLocale)
      return context.getString(
        CopyR.string.export_pdf_subheading,
        now.format(mediumDateFormatter),
        now.format(timeFormatter),
      )
    }

    private suspend fun List<GroupedHealthUiSchemas>.fromGroupedHealthUiSchemasToTable(): List<MgoPdf.Tables> =
      mapNotNull { groupedUiSchemas ->
        val tables = groupedUiSchemas.uiSchemas.fromHealthUiSchemaToTables()
        if (tables.isEmpty()) return@mapNotNull null
        MgoPdf.Tables(
          heading = context.getString(groupedUiSchemas.heading),
          tables = tables,
        )
      }

    private fun List<HealthUiSchema>.fromHealthUiSchemaToTables(): List<MgoPdf.Table> =
      mapNotNull { uiSchema ->
        // Only show sections that we want in the pdf
        val sections = uiSchema.children.filterNot { it.excludeFromPrint ?: false }

        // If there are no sections do not show the table
        if (sections.isEmpty()) return@mapNotNull null

        // Create the table
        MgoPdf.Table(
          sections =
            uiSchema.children.filterNot { it.excludeFromPrint ?: false }.mapIndexed { index, group ->
              val heading = if (index == 0) uiSchema.label else group.label ?: ""
              group.toSections(heading)
            },
        )
      }

    private fun HealthUiGroup.toSections(heading: String) =
      MgoPdf.Section(
        heading = heading,
        rows = children.map { uiElement -> uiElement.toRow() },
      )

    private fun UiElement.toRow(): MgoPdf.Row {
      val emptyText = context.getString(CopyR.string.common_unknown)
      return when (this) {
        is DownloadBinary -> {
          MgoPdf.Row(label = label, content = listOf(), labelColor = LogoBlue500, labelIcon = getAttachmentIconBytes())
        }

        is DownloadLink -> {
          MgoPdf.Row(label = label, content = listOf(), labelColor = LogoBlue500, labelIcon = getAttachmentIconBytes())
        }

        is MultipleGroupedValues -> {
          MgoPdf.Row(
            label = label,
            content =
              value?.flatMap { value -> value.map { display -> display.display ?: emptyText } } ?: listOf(),
          )
        }

        is MultipleValues -> {
          MgoPdf.Row(label = label, content = value?.map { display -> display.display ?: emptyText } ?: listOf())
        }

        is ReferenceLink -> {
          MgoPdf.Row(label = label, content = listOf())
        }

        is ReferenceValue -> {
          MgoPdf.Row(label = label, content = listOf(reference ?: emptyText))
        }

        is SingleValue -> {
          MgoPdf.Row(label = label, content = listOf(value?.display ?: emptyText))
        }
      }
    }

    private fun getAttachmentIconBytes(): ByteArray? {
      val drawable = ContextCompat.getDrawable(context, R.drawable.ic_attachment)
      return drawable?.toBitmap()?.let { bitmap ->
        ByteArrayOutputStream().use { stream ->
          bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
          stream.toByteArray()
        }
      }
    }
  }
