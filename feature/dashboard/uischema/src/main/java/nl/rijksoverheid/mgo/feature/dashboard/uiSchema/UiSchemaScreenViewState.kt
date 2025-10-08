package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.mapper.UISchemaSection

internal data class UiSchemaScreenViewState(
  val toolbarTitle: String,
  val sections: List<UISchemaSection>,
)
