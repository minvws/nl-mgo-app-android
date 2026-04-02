package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import getString
import nl.rijksoverheid.mgo.component.pdf.CreatePdf
import nl.rijksoverheid.mgo.component.pdf.MgoPdf
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DownloadBinary
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DownloadLink
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiGroup
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MultipleGroupedValues
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MultipleValues
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ReferenceLink
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ReferenceValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.SingleValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.UiElement
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.groupBySubCategory
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

interface CreatePdfHealthCategory {
  suspend operator fun invoke(
    mgoResources: List<MgoResource>,
    category: HealthCategoryGroup.HealthCategory,
  ): File
}
