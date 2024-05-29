package nl.rijksoverheid.mgo.data.medication

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.medication.models.MgoMedicationStatement
import nl.rijksoverheid.mgo.data.medication.models.toMgoMedicationStatement
import javax.inject.Inject

internal class DefaultMedicationRepository
    @Inject
    constructor(private val dvaApi: DvaApi) : MedicationRepository {
        override suspend fun getMedications(): Result<List<MgoMedicationStatement>> {
            val result = executeNetworkRequest { dvaApi.medicationStatement() }
            return result.mapCatching { statements ->
                statements.map { statement ->
                    statement.toMgoMedicationStatement()
                }
            }
        }
    }
