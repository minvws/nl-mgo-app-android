package nl.rijksoverheid.mgo.component.pdf

import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema
import java.io.File

interface CreatePdfForUiSchemas {
  operator fun invoke(uiSchemas: List<HealthUiSchema>): File
}
