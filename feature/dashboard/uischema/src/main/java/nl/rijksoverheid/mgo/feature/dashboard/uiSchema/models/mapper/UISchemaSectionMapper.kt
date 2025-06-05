package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.mapper

import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.UISchemaSection

internal interface UISchemaSectionMapper {
  suspend fun map(uiSchema: HealthUiSchema): List<UISchemaSection>
}
