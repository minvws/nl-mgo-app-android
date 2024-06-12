package nl.rijksoverheid.mgo.data.laboratoryTestResult

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.laboratoryTestResult.models.MgoLaboratoryTestResult
import nl.rijksoverheid.mgo.data.laboratoryTestResult.models.toMgoLaboratoryTestResult
import javax.inject.Inject

internal class DefaultLaboratoryTestResultRepository
@Inject
constructor(private val dvaApi: DvaApi) : LaboratoryTestResultRepository {
    override suspend fun getLaboratoryTestResults(): Result<List<MgoLaboratoryTestResult>> {
        val result = executeNetworkRequest { dvaApi.observation() }
        return result.mapCatching { statements ->
            statements.map { statement ->
                statement.toMgoLaboratoryTestResult()
            }
        }
    }
}
