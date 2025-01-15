package nl.rijksoverheid.mgo.data.healthcare.util

import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.urlCreator.HealthCareUrlCreator

class TestHealthCareUrlCreator : HealthCareUrlCreator {
    private var url: String = ""

    fun setUrl(url: String) {
        this.url = url
    }

    override fun invoke(
        baseUrl: String,
        request: HealthCareRequest,
    ): String {
        return url
    }
}
