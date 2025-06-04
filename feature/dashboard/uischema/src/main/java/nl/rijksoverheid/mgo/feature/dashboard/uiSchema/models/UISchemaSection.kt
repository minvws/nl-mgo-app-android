package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models

/**
 * Represents a group of [UISchemaRow].
 *
 * @param heading The heading for this group.
 * @param rows A list of [UISchemaRow] that belong to this group.
 */
internal data class UISchemaSection(
  val heading: String?,
  val rows: List<UISchemaRow>,
)
