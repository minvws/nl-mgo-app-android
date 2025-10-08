package nl.rijksoverheid.mgo.data.healthcare.models.mapper

import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.healthcare.models.UISchemaSection

interface UISchemaSectionMapper {
  suspend fun map(uiSchema: HealthUiSchema): List<UISchemaSection>
}
