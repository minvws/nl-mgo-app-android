package nl.rijksoverheid.mgo.feature.onboarding.proposition

import nl.rijksoverheid.mgo.data.onboarding.HasSeenOnboarding
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.TestEnvironmentRepository
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MemoryMgoKeyValueStorage
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class PropositionScreenViewModelTest {
  private val keyValueStorage = MemoryMgoKeyValueStorage()
  private val setHasSeenOnboarding = SetHasSeenOnboarding(keyValueStorage)
  private val hasSeenOnboarding = HasSeenOnboarding(keyValueStorage)

  @Test
  fun `Given ViewModel, When setHasSeenOnboarding is called, Then use case is called`() {
    // Given
    val environmentRepository = TestEnvironmentRepository()
    environmentRepository.setEnvironment(Environment.Prod(versionCode = 1, deeplinkHost = "mgo"))
    val viewModel =
      PropositionScreenViewModel(
        environmentRepository = environmentRepository,
        setHasSeenOnboarding = setHasSeenOnboarding,
      )

    // When
    viewModel.setHasSeenOnboarding()

    // Then
    assertTrue(hasSeenOnboarding.invoke())
  }

  @Test
  fun testGetPrivacyUrlForTst() {
    // Given: Tst environment
    val environmentRepository = TestEnvironmentRepository()
    environmentRepository.setEnvironment(Environment.Tst(1, "mgo"))

    // Given: Viewmodel
    val viewModel =
      PropositionScreenViewModel(
        environmentRepository = environmentRepository,
        setHasSeenOnboarding = setHasSeenOnboarding,
      )

    // When: Calling get privacy url
    val privacyUrl = viewModel.getPrivacyUrl()

    // Then: Url is expected
    assertEquals("https://web.test.mgo.irealisatie.nl/privacy", privacyUrl)
  }

  @Test
  fun testGetPrivacyUrlForAcc() {
    // Given: Acc environment
    val environmentRepository = TestEnvironmentRepository()
    environmentRepository.setEnvironment(Environment.Acc(1, "mgo"))

    // Given: Viewmodel
    val viewModel =
      PropositionScreenViewModel(
        environmentRepository = environmentRepository,
        setHasSeenOnboarding = setHasSeenOnboarding,
      )

    // When: Calling get privacy url
    val privacyUrl = viewModel.getPrivacyUrl()

    // Then: Url is expected
    assertEquals("https://web.test.mgo.irealisatie.nl/privacy", privacyUrl)
  }

  @Test
  fun testGetPrivacyUrlForProd() {
    // Given: Prod environment
    val environmentRepository = TestEnvironmentRepository()
    environmentRepository.setEnvironment(Environment.Prod(1, "mgo"))

    // Given: Viewmodel
    val viewModel =
      PropositionScreenViewModel(
        environmentRepository = environmentRepository,
        setHasSeenOnboarding = setHasSeenOnboarding,
      )

    // When: Calling get privacy url
    val privacyUrl = viewModel.getPrivacyUrl()

    // Then: Url is expected
    assertEquals("https://web.test.mgo.irealisatie.nl/privacy", privacyUrl)
  }

  @Test
  fun testGetPrivacyUrlForDemo() {
    // Given: Demo environment
    val environmentRepository = TestEnvironmentRepository()
    environmentRepository.setEnvironment(Environment.Demo(1, "mgo"))

    // Given: Viewmodel
    val viewModel =
      PropositionScreenViewModel(
        environmentRepository = environmentRepository,
        setHasSeenOnboarding = setHasSeenOnboarding,
      )

    // When: Calling get privacy url
    val privacyUrl = viewModel.getPrivacyUrl()

    // Then: Url is expected
    assertEquals("https://web.test.mgo.irealisatie.nl/privacy", privacyUrl)
  }

  @Test
  fun testGetPrivacyUrlForCustom() {
    // Given: Demo environment
    val environmentRepository = TestEnvironmentRepository()
    environmentRepository.setEnvironment(Environment.Custom(1, "mgo", "https://google.nl"))

    // Given: Viewmodel
    val viewModel =
      PropositionScreenViewModel(
        environmentRepository = environmentRepository,
        setHasSeenOnboarding = setHasSeenOnboarding,
      )

    // When: Calling get privacy url
    val privacyUrl = viewModel.getPrivacyUrl()

    // Then: Url is expected
    assertEquals("https://web.test.mgo.irealisatie.nl/privacy", privacyUrl)
  }
}
