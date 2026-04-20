package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import nl.rijksoverheid.mgo.component.uiSchema.UISchemaSection
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema

internal data class UiSchemaScreenViewState(
  val toolbarTitle: String,
  val uiSchema: HealthUiSchema?,
  val sections: List<UISchemaSection>,
)
