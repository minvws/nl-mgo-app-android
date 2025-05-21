package nl.rijksoverheid.mgo.data.healthcare.mgoResource.urlCreator

import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest

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
