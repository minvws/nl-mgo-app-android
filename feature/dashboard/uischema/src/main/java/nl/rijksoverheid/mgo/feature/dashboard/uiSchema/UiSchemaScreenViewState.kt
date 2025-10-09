package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import nl.rijksoverheid.mgo.component.uiSchema.UISchemaSection

internal data class UiSchemaScreenViewState(
  val toolbarTitle: String,
  val sections: List<UISchemaSection>,
)
