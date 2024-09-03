package nl.rijksoverheid.mgo.data.medication

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import nl.rijksoverheid.mgo.data.uiSchema.UiSchemaMapper
import javax.inject.Inject

internal class DefaultMedicationRepository
    @Inject
    constructor(private val dvaApi: DvaApi, private val uiSchemaMapper: UiSchemaMapper) : MedicationRepository {
        override suspend fun getMedications(resourceEndpoint: String): Result<List<UISchema>> {
            val responseBody = executeNetworkRequest { dvaApi.medicationStatement(resourceEndpoint) }
            responseBody.exceptionOrNull()?.let { exception ->
                return Result.failure(exception)
            }
            val responseJson = responseBody.getOrNull()?.string() ?: return Result.failure(IllegalStateException("No response body"))
            val uiSchemaResult = uiSchemaMapper.getUiSchema(responseJson)
            return uiSchemaResult
        }
    }
