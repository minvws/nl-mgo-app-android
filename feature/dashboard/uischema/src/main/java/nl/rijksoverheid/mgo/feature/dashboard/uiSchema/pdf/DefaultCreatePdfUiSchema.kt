package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.pdf

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import getString
import nl.rijksoverheid.mgo.component.pdf.CreatePdf
import nl.rijksoverheid.mgo.component.pdf.MgoPdf
import nl.rijksoverheid.mgo.component.pdf.toRow
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DisplayValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DownloadBinary
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DownloadLink
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiGroup
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MultipleGroupedValues
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MultipleValues
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ReferenceLink
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ReferenceValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.SingleValue
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

internal class DefaultCreatePdfUiSchema
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
    @Named("systemDefaultZone") private val clock: Clock,
    private val createPdf: CreatePdf,
  ) : CreatePdfUiSchema {
    override suspend fun invoke(uiSchema: HealthUiSchema): File {
      val uiSchema =
        HealthUiSchema(
          label = "Label",
          children =
            listOf(
              HealthUiGroup(
                children =
                  listOf(
                    SingleValue(id = "1", label = "Single Value", value = DisplayValue(display = "Value")),
                    SingleValue(id = "1", label = "Single Value Empty", value = DisplayValue(display = null)),
                    MultipleValues(
                      id = "1",
                      label = "Multiple Value",
                      value = listOf(DisplayValue(display = "Value 1"), DisplayValue(display = "Value 2")),
                    ),
                    MultipleValues(id = "1", label = "Multiple Value Empty", value = null),
                    MultipleGroupedValues(
                      id = "1",
                      label = "Multiple Grouped Values",
                      value =
                        listOf(
                          listOf(
                            DisplayValue(display = "Value 1"),
                            DisplayValue(display = "Value 2"),
                          ),
                          listOf(DisplayValue(display = "Value 3"), DisplayValue(display = "Value 4")),
                        ),
                    ),
                    MultipleGroupedValues(
                      id = "1",
                      label = "Multiple Grouped Values Empty",
                      value = null,
                    ),
                    ReferenceLink(
                      id = "1",
                      reference = "",
                      label = "Reference Link",
                    ),
                    ReferenceValue(
                      id = "1",
                      label = "Reference Value",
                      reference = "Reference",
                    ),
                    ReferenceValue(
                      id = "1",
                      label = "Reference Value Empty",
                      reference = null,
                    ),
                  ),
              ),
              HealthUiGroup(
                label = "Downloads",
                children =
                  listOf(
                    DownloadBinary(
                      id = "1",
                      label = "Download Binary",
                      reference = null,
                    ),
                    DownloadLink(
                      id = "1",
                      label = "Download Link",
                    ),
                  ),
              ),
            ),
        )

      val pdf = uiSchema.toMgoPdf()
      return createPdf(pdf)
    }

    private fun HealthUiSchema.toMgoPdf(): MgoPdf =
      MgoPdf(
        fileName = getFileName(label),
        heading = label,
        subheading = getSubheading(),
        tables = children.filterNot { it.excludeFromPrint ?: false }.map { group -> group.toTables() },
      )

    private fun getFileName(label: String): String {
      val now = LocalDateTime.now(clock)
      return buildString {
        append("mgo")
        append("_")
        append(label.lowercase().replace(" ", "_"))
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

    private fun HealthUiGroup.toTables() =
      MgoPdf.Tables(
        heading = label,
        tables =
          listOf(MgoPdf.Table(sections = listOf(MgoPdf.Section(heading = null, rows = children.map { child -> child.toRow(context) })))),
      )
  }
