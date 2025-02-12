package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models

import nl.rijksoverheid.mgo.data.fhirParser.models.UiSchema

data class UISchemaSection(
    val heading: String?,
    val rows: List<UISchemaRow>,
)

internal fun UiSchema.toSections(): List<UISchemaSection> {
    return this.children.map { uiSchemaChild ->
        UISchemaSection(
            heading = uiSchemaChild.label,
            rows = uiSchemaChild.children.map { uiElement -> uiElement.toRow() },
        )
    }
}
