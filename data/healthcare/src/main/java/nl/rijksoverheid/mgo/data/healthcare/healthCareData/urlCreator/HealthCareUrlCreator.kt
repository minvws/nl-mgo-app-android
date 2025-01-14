package nl.rijksoverheid.mgo.data.healthcare.healthCareData.urlCreator

import nl.rijksoverheid.mgo.data.healthcare.healthCareData.HealthCareRequest

interface HealthCareUrlCreator {
    /**
     * Creates an url based on a base url and a [HealthCareRequest]
     * @param baseUrl The base url
     * @param request The request
     * @return The url that is created from the base url and the request
     */
    operator fun invoke(
        baseUrl: String,
        request: HealthCareRequest,
    ): String
}
