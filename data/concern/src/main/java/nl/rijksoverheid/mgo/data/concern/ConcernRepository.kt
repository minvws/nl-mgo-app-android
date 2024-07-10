package nl.rijksoverheid.mgo.data.concern

import nl.rijksoverheid.mgo.data.concern.models.MgoConcern

interface ConcernRepository {
    suspend fun getConcerns(resourceEndpoint: String): Result<List<MgoConcern>>
}
