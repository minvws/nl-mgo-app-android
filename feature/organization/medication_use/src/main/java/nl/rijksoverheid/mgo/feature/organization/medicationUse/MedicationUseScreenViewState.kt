package nl.rijksoverheid.mgo.feature.organization.medicationUse

import nl.rijksoverheid.mgo.data.uiSchema.UISchema

data class MedicationUseScreenViewState(
    val uiSchemaList: List<UISchema>,
) {
    companion object {
        val initialState =
            MedicationUseScreenViewState(
                uiSchemaList = listOf(),
            )
    }
}
