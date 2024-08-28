package nl.rijksoverheid.mgo.data.medication

import com.squareup.moshi.rawType
import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.medication.models.MgoMedication
import nl.rijksoverheid.mgo.data.medication.models.toMedication
import nl.rijksoverheid.mgo.data.uiSchema.UiSchemaMapper
import nl.rijksoverheid.mgo.framework.test.toJsonString
import timber.log.Timber
import java.lang.reflect.ParameterizedType
import javax.inject.Inject

internal class DefaultMedicationRepository
    @Inject
    constructor(private val dvaApi: DvaApi, private val uiSchemaMapper: UiSchemaMapper) : MedicationRepository {
        override suspend fun getMedications(resourceEndpoint: String): Result<List<MgoMedication>> {
            val responseBody = executeNetworkRequest { dvaApi.medicationStatement(resourceEndpoint) }
            responseBody.exceptionOrNull()?.let { exception ->
                return Result.failure(exception)
            }

            val responseJson = responseBody.getOrNull()?.string() ?: return Result.failure(IllegalStateException("No response body"))
            val uiSchemaResult = uiSchemaMapper.getUiSchema(responseJson)
            Timber.v("Result: " + uiSchemaResult)

            return Result.failure(IllegalStateException("Not yet implemented"))
        }
    }
