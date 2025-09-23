package nl.rijksoverheid.mgo.data.healthData.health

/**
 * Initializes fetching of the health data of all stored organizations.
 */
interface InitHealthDataFetching {
  suspend operator fun invoke()
}
