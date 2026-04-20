package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import getString
import nl.rijksoverheid.mgo.component.pdf.CreatePdf
import nl.rijksoverheid.mgo.component.pdf.MgoPdf
import nl.rijksoverheid.mgo.component.pdf.toRow
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiGroup
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
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

    private fun List<GroupedHealthUiSchemas>.toMgoPdf(category: HealthCategoryGroup.HealthCategory): MgoPdf {
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

    private fun List<GroupedHealthUiSchemas>.fromGroupedHealthUiSchemasToTable(): List<MgoPdf.Tables> =
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
        uiSchema.toMgoPdfTable(context)
      }

    private fun HealthUiSchema.toMgoPdfTable(context: Context): MgoPdf.Table? {
      val sections = children.filterNot { it.excludeFromPrint ?: false }

      // If there are no sections do not show the table
      if (sections.isEmpty()) return null

      // Create the table
      return MgoPdf.Table(
        sections =
          children.filterNot { it.excludeFromPrint ?: false }.mapIndexed { index, group ->
            val heading = if (index == 0) label else group.label ?: ""
            group.toSections(context = context, heading = heading)
          },
      )
    }

    private fun HealthUiGroup.toSections(
      context: Context,
      heading: String,
    ) = MgoPdf.Section(
      heading = heading,
      rows = children.map { uiElement -> uiElement.toRow(context) },
    )
  }
