package nl.rijksoverheid.mgo.feature.organization.labResults

import nl.rijksoverheid.mgo.data.laboratoryTestResult.models.MgoLaboratoryTestResult

data class LabResultsScreenViewState(
    val loading: Boolean,
    val testResults: List<MgoLaboratoryTestResult>,
    val error: Throwable?,
) {
    companion object {
        val initialState =
            LabResultsScreenViewState(
                loading = true,
                testResults = listOf(),
                error = null,
            )
    }
}
