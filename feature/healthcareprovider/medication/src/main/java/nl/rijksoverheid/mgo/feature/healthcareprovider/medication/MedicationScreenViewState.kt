package nl.rijksoverheid.mgo.feature.healthcareprovider.medication

import nl.rijksoverheid.mgo.data.medication.models.MgoMedication

data class MedicationScreenViewState(
    val loading: Boolean,
    val medications: List<MgoMedication>,
    val error: Throwable?,
) {
    companion object {
        val initialState = MedicationScreenViewState(loading = true, medications = listOf(), error = null)
    }
}
