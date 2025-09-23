package nl.rijksoverheid.mgo.data.healthData.health

import kotlinx.coroutines.flow.Flow
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization

/**
 * Initializes fetching of the health data of all stored organizations.
 *
 * @return a [Flow] that when collecting on will start the fetching of the health data.
 */
interface InitHealthDataFetching {
  suspend operator fun invoke(): Flow<List<MgoOrganization>>
}
