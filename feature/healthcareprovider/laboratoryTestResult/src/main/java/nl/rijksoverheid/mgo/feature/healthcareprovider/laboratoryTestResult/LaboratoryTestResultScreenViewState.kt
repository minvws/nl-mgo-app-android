package nl.rijksoverheid.mgo.feature.healthcareprovider.laboratoryTestResult

import nl.rijksoverheid.mgo.data.laboratoryTestResult.models.MgoLaboratoryTestResult

data class LaboratoryTestResultScreenViewState(
    val loading: Boolean,
    val testResults: List<MgoLaboratoryTestResult>,
    val error: Throwable?,
) {
    companion object {
        val initialState =
            LaboratoryTestResultScreenViewState(
                loading = true,
                testResults = listOf(),
                error = null,
            )
    }
}
