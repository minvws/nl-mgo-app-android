package nl.rijksoverheid.mgo.feature.dashboard.healthCategory.pdf

import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema

data class GroupedHealthUiSchemas(
  val heading: String,
  val uiSchemas: List<HealthUiSchema>,
)
