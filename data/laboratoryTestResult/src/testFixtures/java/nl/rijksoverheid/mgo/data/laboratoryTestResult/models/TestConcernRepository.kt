package nl.rijksoverheid.mgo.data.laboratoryTestResult.models

import nl.rijksoverheid.mgo.data.laboratoryTestResult.LaboratoryTestResultRepository

class TestLaboratoryTestResultRepository(private val result: Result<List<MgoLaboratoryTestResult>>) :
    LaboratoryTestResultRepository {
    override suspend fun getLaboratoryTestResults(): Result<List<MgoLaboratoryTestResult>> {
        return result
    }
}
