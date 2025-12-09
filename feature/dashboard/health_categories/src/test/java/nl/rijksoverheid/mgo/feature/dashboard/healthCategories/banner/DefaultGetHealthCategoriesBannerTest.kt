package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.banner

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_RESPONSE_ERROR_SERVER
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_RESPONSE_ERROR_USER
import nl.rijksoverheid.mgo.data.fhir.TEST_FHIR_RESPONSE_SUCCESS
import nl.rijksoverheid.mgo.data.fhir.TestFhirRepository
import nl.rijksoverheid.mgo.data.healthCategories.GetEndpointsForHealthCategory
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetDataSetsFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.JvmGetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class DefaultGetHealthCategoriesBannerTest {
  private val getDataSetsFromDisk = JvmGetDataSetsFromDisk()
  private val getEndpointsForHealthCategory = GetEndpointsForHealthCategory(getDataSetsFromDisk)
  private val getHealthCategoriesFromDisk = JvmGetHealthCategoriesFromDisk()
  private val organizationRepository = OrganizationRepository(okHttpClient = OkHttpClient(), baseUrl = "", mgoByteArrayStorage = MemoryMgoByteArrayStorage())
  private val fhirRepository = TestFhirRepository()
  private val getHealthCategoriesBanner =
    DefaultGetHealthCategoriesBanner(
      organizationRepository = organizationRepository,
      getHealthCategoriesFromDisk = getHealthCategoriesFromDisk,
      getEndpointsForHealthCategory = getEndpointsForHealthCategory,
      fhirRepository = fhirRepository,
    )

  @Test
  fun testUserErrorBanner() =
    runTest {
      // Given: Organization is stored
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: All fhir responses that are requested fail
      val responses = List(28) { TEST_FHIR_RESPONSE_ERROR_USER }
      fhirRepository.setObserveResults(responses)

      // When: Observing the banner
      getHealthCategoriesBanner.invoke().test {
        // Then: Banner is emitted
        assertEquals(HealthCategoriesBannerState.Error.UserError(false), awaitItem())
      }
    }

  @Test
  fun testUserErrorPartialBanner() =
    runTest {
      // Given: Organization is stored
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: One fhir response that is requested fails
      val successResponses = List(27) { TEST_FHIR_RESPONSE_SUCCESS() }
      val failedResponses = List(1) { TEST_FHIR_RESPONSE_ERROR_USER }
      fhirRepository.setObserveResults(successResponses + failedResponses)

      // When: Observing the banner
      getHealthCategoriesBanner.invoke().test {
        // Then: Banner is emitted
        assertEquals(HealthCategoriesBannerState.Error.UserError(true), awaitItem())
      }
    }

  @Test
  fun testServerErrorBanner() =
    runTest {
      // Given: Organization is stored
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: All fhir responses that are requested fail
      val responses = List(28) { TEST_FHIR_RESPONSE_ERROR_SERVER }
      fhirRepository.setObserveResults(responses)

      // When: Observing the banner
      getHealthCategoriesBanner.invoke().test {
        // Then: Banner is emitted
        assertEquals(HealthCategoriesBannerState.Error.ServerError(false), awaitItem())
      }
    }

  @Test
  fun testServerErrorPartialBanner() =
    runTest {
      // Given: Organization is stored
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: One fhir response that is requested fails
      val successResponses = List(27) { TEST_FHIR_RESPONSE_SUCCESS() }
      val failedResponses = List(1) { TEST_FHIR_RESPONSE_ERROR_SERVER }
      fhirRepository.setObserveResults(successResponses + failedResponses)

      // When: Observing the banner
      getHealthCategoriesBanner.invoke().test {
        // Then: Banner is emitted
        assertEquals(HealthCategoriesBannerState.Error.ServerError(true), awaitItem())
      }
    }

  @Test
  fun testLoadedBanner() =
    runTest {
      // Given: Organization is stored
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: All fhir responses that are requested are success
      val responses = List(28) { TEST_FHIR_RESPONSE_SUCCESS() }
      fhirRepository.setObserveResults(responses)

      // When: Observing the banner
      getHealthCategoriesBanner.invoke().test {
        // Then: Banner is emitted
        assertNull(awaitItem())
      }
    }

  @Test
  fun testLoadingBanner() =
    runTest {
      // Given: Organization is stored
      organizationRepository.save(TEST_MGO_ORGANIZATION)

      // Given: One fhir response is success (the rest is still loading)
      fhirRepository.setObserveResults(listOf(TEST_FHIR_RESPONSE_SUCCESS()))

      // When: Observing the banner
      getHealthCategoriesBanner.invoke().test {
        // Then: Banner is emitted
        assertEquals(HealthCategoriesBannerState.Loading, awaitItem())
      }
    }
}
