package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.pdf

import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema
import java.io.File

interface CreatePdfUiSchema {
  suspend operator fun invoke(uiSchema: HealthUiSchema): File
}
