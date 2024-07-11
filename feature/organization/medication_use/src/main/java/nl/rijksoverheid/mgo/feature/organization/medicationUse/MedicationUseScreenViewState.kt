package nl.rijksoverheid.mgo.feature.healthcareprovider.medication

import nl.rijksoverheid.mgo.data.medication.models.MgoMedication

data class MedicationUseScreenViewState(
    val providerName: String,
    val loading: Boolean,
    val medications: List<MgoMedication>,
    val error: Throwable?,
) {
    companion object {
        fun initialState(providerName: String) =
            MedicationUseScreenViewState(
                providerName = providerName,
                loading = true,
                medications = listOf(),
                error = null,
            )
    }
}
