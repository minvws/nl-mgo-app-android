package nl.rijksoverheid.mgo.data.laboratoryTestResult

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.laboratoryTestResult.models.MgoLaboratoryTestResult
import nl.rijksoverheid.mgo.data.laboratoryTestResult.models.toMgoLaboratoryTestResult
import javax.inject.Inject

internal class DefaultLaboratoryTestResultRepository
    @Inject
    constructor(private val dvaApi: DvaApi, private val dvaApiBaseUrl: String) : LaboratoryTestResultRepository {
        override suspend fun getLaboratoryTestResults(resourceEndpoint: String): Result<List<MgoLaboratoryTestResult>> {
            val result =
                executeNetworkRequest {
                    dvaApi.observation(
                        resourceEndpoint = resourceEndpoint,
                        url =
                            "${dvaApiBaseUrl}fhir/Observation/\$lastn?_format=json" +
                                "&category=http://snomed.info/sct|275711006" +
                                "&_include=Observation:related-target&_include=Observation:specimen",
                    )
                }
            return result.mapCatching { statements ->
                statements.map { statement ->
                    statement.toMgoLaboratoryTestResult()
                }
            }
        }
    }
