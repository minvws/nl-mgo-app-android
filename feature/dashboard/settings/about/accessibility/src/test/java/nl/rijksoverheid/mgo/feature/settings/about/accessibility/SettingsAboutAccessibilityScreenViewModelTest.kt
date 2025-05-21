package nl.rijksoverheid.mgo.feature.settings.about.accessibility

import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.TestEnvironmentRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

internal class SettingsAboutAccessibilityScreenViewModelTest {
  private val environmentRepository = TestEnvironmentRepository()
  private val viewModel = SettingsAboutAccessibilityScreenViewModel(environmentRepository = environmentRepository)

  @Test
  fun testUrlTestEnv() {
    // Given: Test environment
    environmentRepository.setEnvironment(Environment.Tst(1, ""))

    // When: Calling get url
    val url = viewModel.getUrl()

    // Then: Return correct url
    assertEquals(CopyR.string.settings_accessibility_more_information_url_test, url)
  }

  @Test
  fun testUrlAccEnv() {
    // Given: Test environment
    environmentRepository.setEnvironment(Environment.Acc(1, ""))

    // When: Calling get url
    val url = viewModel.getUrl()

    // Then: Return correct url
    assertEquals(CopyR.string.settings_accessibility_more_information_url_acc, url)
  }

  @Test
  fun testUrlProdEnv() {
    // Given: Test environment
    environmentRepository.setEnvironment(Environment.Prod(1, ""))

    // When: Calling get url
    val url = viewModel.getUrl()

    // Then: Return correct url
    assertEquals(CopyR.string.settings_accessibility_more_information_url_prod, url)
  }

  @Test
  fun testUrlDemoEnv() {
    // Given: Test environment
    environmentRepository.setEnvironment(Environment.Demo(1, ""))

    // When: Calling get url
    val url = viewModel.getUrl()

    // Then: Return correct url
    assertEquals(CopyR.string.settings_accessibility_more_information_url_acc, url)
  }

  @Test
  fun testUrlCustomEnv() {
    // Given: Test environment
    environmentRepository.setEnvironment(Environment.Custom(1, "", ""))

    // When: Calling get url
    val url = viewModel.getUrl()

    // Then: Return correct url
    assertEquals(CopyR.string.settings_accessibility_more_information_url_test, url)
  }
}
