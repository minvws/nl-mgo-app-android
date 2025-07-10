package nl.rijksoverheid.mgo.data.healthcare.models

import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema

/**
 * Represents a group of [UISchemaRow].
 *
 * @param heading The heading for this group.
 * @param rows A list of [UISchemaRow] that belong to this group.
 */
data class UISchemaSection(
  val heading: String?,
  val rows: List<UISchemaRow>,
)

fun HealthUiSchema.toSections(): List<UISchemaSection> =
  this.children.map { uiSchemaChild ->
    UISchemaSection(
      heading = uiSchemaChild.label,
      rows = uiSchemaChild.children.map { uiElement -> uiElement.toRow() },
    )
  }
