package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.pdf

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import getString
import nl.rijksoverheid.mgo.component.pdf.CreatePdf
import nl.rijksoverheid.mgo.component.pdf.MgoPdf
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema
import java.io.File
import java.time.Clock
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
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
      val pdf = uiSchema.toMgoPdf()
      return createPdf(pdf)
    }

    private suspend fun HealthUiSchema.toMgoPdf(): MgoPdf =
      MgoPdf(
        fileName = "test.pdf",
        heading = label,
        subheading = getSubheading(),
        tables = listOf(),
      )

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
  }
