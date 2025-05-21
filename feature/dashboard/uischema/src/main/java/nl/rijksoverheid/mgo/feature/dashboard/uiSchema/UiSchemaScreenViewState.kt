package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.UISchemaSection

/**
 * The view state for [UiSchemaScreen].
 *
 * @param toolbarTitle The title of the screen.
 * @param sections A list of [UISchemaSection].
 */
internal data class UiSchemaScreenViewState(
  val toolbarTitle: String,
  val sections: List<UISchemaSection>,
)
