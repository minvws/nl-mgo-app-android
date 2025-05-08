package nl.rijksoverheid.mgo.data.healthcare.mgoResource.urlCreator

import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareRequest
import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultHealthCareUrlCreatorTest {
  private val urlCreator = DefaultHealthCareUrlCreator()

  @Test
  fun testCreateUrl() {
    // Given: Base url
    val baseUrl = "https://example.com"

    // Given: Request with query parameters
    val request = HealthCareRequest.Bgz.MedicationUse

    // When: Calling url creator
    val url = urlCreator.invoke(baseUrl = baseUrl, request = request)

    // Then: Url is created
    assertEquals(
      "https://example.com?category=urn%3Aoid%3A2.16.840.1.113883.2.4.3.11.60.20.77.5.3%7C" +
        "6&_include=MedicationStatement%3Amedication",
      url,
    )
  }
}
