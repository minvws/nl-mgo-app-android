package nl.rijksoverheid.mgo.feature.organization.medicationUse

import nl.rijksoverheid.mgo.data.uiSchema.UISchema

data class MedicationUseScreenViewState(
    val providerName: String,
    val loading: Boolean,
    val uiSchemaList: List<UISchema>,
    val error: Throwable?,
) {
    companion object {
        fun initialState(providerName: String) =
            MedicationUseScreenViewState(
                providerName = providerName,
                loading = true,
                uiSchemaList = listOf(),
                error = null,
            )
    }
}
