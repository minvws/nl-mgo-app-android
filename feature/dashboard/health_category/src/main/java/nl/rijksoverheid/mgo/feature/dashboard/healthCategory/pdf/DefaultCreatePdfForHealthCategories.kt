package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import nl.rijksoverheid.mgo.component.healthCategories.getString
import nl.rijksoverheid.mgo.component.pdfViewer.Pdf
import nl.rijksoverheid.mgo.component.pdfViewer.PdfGenerator
import nl.rijksoverheid.mgo.component.pdfViewer.PdfGroupedTables
import nl.rijksoverheid.mgo.component.pdfViewer.PdfSubTable
import nl.rijksoverheid.mgo.component.pdfViewer.PdfTable
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaSectionMapper
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenListItemsGroup
import nl.rijksoverheid.mgo.framework.copy.R
import java.io.File
import java.time.Clock
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject
import javax.inject.Named

internal class DefaultCreatePdfForHealthCategories
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
    @Named("systemDefaultZone") private val clock: Clock,
    private val uiSchemaParser: UiSchemaParser,
    private val uiSchemaSectionMapper: UISchemaSectionMapper,
    private val pdfGenerator: PdfGenerator,
  ) : CreatePdfForHealthCategories {
    override suspend fun invoke(
      category: HealthCategoryGroup.HealthCategory,
      listItemGroups: List<HealthCategoryScreenListItemsGroup>,
    ): File {
      val categoryTitle = context.getString(category.heading)
      val deviceLocale = Locale.getDefault()
      val now = LocalDateTime.now(clock)
      val mediumDateFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM).withLocale(deviceLocale)
      val timeFormatter = DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(deviceLocale)
      val groupedPdfTables = listItemGroups.toPdfTables()
      val pdf =
        Pdf(
          heading = categoryTitle,
          subHeading =
            context.getString(
              R.string.export_pdf_subheading,
              now.format(mediumDateFormatter),
              now.format(timeFormatter),
            ),
          groupedTables = groupedPdfTables,
          footer = context.getString(R.string.export_pdf_footer),
        )

      val file =
        pdfGenerator.invoke(
          pdf = pdf,
          fileName = getFileName(now = now, categoryTitle = categoryTitle),
        )

      return file
    }

    private fun getFileName(
      now: LocalDateTime,
      categoryTitle: String,
    ) = buildString {
      append("mgo")
      append("_")
      append(categoryTitle.lowercase().replace(" ", "_"))
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

    private suspend fun List<HealthCategoryScreenListItemsGroup>.toPdfTables(): List<PdfGroupedTables> =
      map { itemsGroup ->
        val pdfTables =
          itemsGroup.items.map { listItem ->
            uiSchemaParser
              .getSummary(organizationName = listItem.organization.name, mgoResourceJson = listItem.mgoResource.json)
              .let { uiSchemaSectionMapper.map(it) }
              .map { section ->
                PdfSubTable(
                  heading = section.heading,
                  data =
                    section.rows.mapNotNull { row ->
                      (row.heading ?: return@mapNotNull null) to row.displayValue
                    },
                )
              }.filter { it.data.isNotEmpty() }
              .let { subTables ->
                PdfTable(
                  heading = listItem.title,
                  subTables = subTables,
                )
              }
          }
        PdfGroupedTables(
          heading = context.getString(itemsGroup.heading),
          tables = pdfTables,
        )
      }
  }
