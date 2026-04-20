package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.pdf

import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema
import java.io.File

class TestCreatePdfUiSchema : CreatePdfUiSchema {
  override suspend fun invoke(uiSchema: HealthUiSchema): File = File("")
}
