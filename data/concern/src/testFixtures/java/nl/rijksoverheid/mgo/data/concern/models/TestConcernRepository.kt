package nl.rijksoverheid.mgo.data.concern.models

import nl.rijksoverheid.mgo.data.concern.ConcernRepository

class TestConcernRepository(private val result: Result<List<MgoConcern>>) : ConcernRepository {
    override suspend fun getConcerns(resourceEndpoint: String): Result<List<MgoConcern>> {
        return result
    }
}
