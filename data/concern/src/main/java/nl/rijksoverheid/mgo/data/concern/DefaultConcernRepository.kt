package nl.rijksoverheid.mgo.data.concern

import nl.nl.rijksoverheid.mgo.framework.network.executeNetworkRequest
import nl.rijksoverheid.mgo.data.api.dva.DvaApi
import nl.rijksoverheid.mgo.data.concern.models.MgoConcern
import nl.rijksoverheid.mgo.data.concern.models.toConcern
import javax.inject.Inject

internal class DefaultConcernRepository
    @Inject
    constructor(private val dvaApi: DvaApi) : ConcernRepository {
        override suspend fun getConcerns(resourceEndpoint: String): Result<List<MgoConcern>> {
            val result = executeNetworkRequest { dvaApi.condition(resourceEndpoint) }
            return result.mapCatching { statements ->
                statements.map { statement ->
                    statement.toConcern()
                }
            }
        }
    }
