package nl.rijksoverheid.mgo.data.healthcare.mgoResource.urlCreator

import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest

/**
 * Create an url based on a base url an a [HealthCareRequest].
 */
interface HealthCareUrlCreator {
  /**
   * @param baseUrl The base url.
   * @param request The [HealthCareRequest].
   * @return The constructed url from the base url and the [HealthCareRequest].
   */
  operator fun invoke(
    baseUrl: String,
    request: HealthCareRequest,
  ): String
}
