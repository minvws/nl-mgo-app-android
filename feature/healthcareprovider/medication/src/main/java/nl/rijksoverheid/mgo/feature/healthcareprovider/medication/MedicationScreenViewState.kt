package nl.rijksoverheid.mgo.feature.healthcareprovider.medication

import nl.rijksoverheid.mgo.data.medication.models.MgoMedication

sealed class MedicationScreenViewState {
    data object Loading : MedicationScreenViewState()

    data class Success(val medications: List<MgoMedication>) : MedicationScreenViewState()

    data class Error(val isProductionBuild: Boolean, val error: Throwable) : MedicationScreenViewState()
}
