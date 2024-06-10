package nl.rijksoverheid.mgo.data.concern

import nl.rijksoverheid.mgo.data.concern.models.MgoConcern

interface ConcernsRepository {
    suspend fun getConcerns(): Result<List<MgoConcern>>
}
