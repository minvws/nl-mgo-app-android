package nl.rijksoverheid.mgo.data.healthcareprovider

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.healthcareprovider.api.DvaApi
import org.hl7.fhir.dstu3.model.MedicationStatement
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class DefaultDvaRepository
    @Inject
    constructor(private val dvaApi: DvaApi) : DvaRepository {
        override suspend fun getMedicationStatement(): Result<List<MedicationStatement>> {
            val result = executeNetworkRequest { dvaApi.medicationStatement() }
            return result
        }
    }
