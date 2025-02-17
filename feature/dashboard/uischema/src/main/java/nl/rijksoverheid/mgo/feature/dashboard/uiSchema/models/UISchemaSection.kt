package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models

import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema

data class UISchemaSection(
    val heading: String?,
    val rows: List<UISchemaRow>,
)

internal fun HealthUiSchema.toSections(): List<UISchemaSection> {
    return this.children.map { uiSchemaChild ->
        UISchemaSection(
            heading = uiSchemaChild.label,
            rows = uiSchemaChild.children.map { uiElement -> uiElement.toRow() },
        )
    }
}
