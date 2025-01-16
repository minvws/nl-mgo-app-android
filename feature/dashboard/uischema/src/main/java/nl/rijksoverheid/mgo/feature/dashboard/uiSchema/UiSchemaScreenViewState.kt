package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.UISchemaSection

data class UiSchemaScreenViewState(
    val toolbarTitle: String,
    val sections: List<UISchemaSection>,
)
