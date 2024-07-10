package nl.rijksoverheid.mgo.data.laboratoryTestResult

import nl.rijksoverheid.mgo.data.laboratoryTestResult.models.MgoLaboratoryTestResult

interface LaboratoryTestResultRepository {
    suspend fun getLaboratoryTestResults(resourceEndpoint: String): Result<List<MgoLaboratoryTestResult>>
}
