package nl.rijksoverheid.mgo.feature.healthcareprovider.laboratoryTestResult

import nl.rijksoverheid.mgo.data.laboratoryTestResult.models.MgoLaboratoryTestResult

sealed class LaboratoryTestResultScreenViewState {
    data object Loading : LaboratoryTestResultScreenViewState()

    data class Success(val testResults: List<MgoLaboratoryTestResult>) : LaboratoryTestResultScreenViewState()

    data class Error(val isProductionBuild: Boolean, val error: Throwable) : LaboratoryTestResultScreenViewState()
}
