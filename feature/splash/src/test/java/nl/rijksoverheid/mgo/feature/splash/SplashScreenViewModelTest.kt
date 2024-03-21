package nl.rijksoverheid.mgo.feature.splash

import app.cash.turbine.test
import nl.rijksoverheid.mgo.data.config.test.TEST_CONFIG
import nl.rijksoverheid.mgo.data.config.test.TestConfigRepository
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import nl.rijksoverheid.mgo.framework.test.TestCoroutineRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

class SplashScreenViewModelTest {
    private val configRepository =
        TestConfigRepository()

    @get:Rule
    val coroutineRule = TestCoroutineRule()

    @Test
    fun `Given viewmodel, When config is fetched successfully, Then navigate to onboarding welcome screen`() =
        runTest {
            // Given
            configRepository.setConfig(TEST_CONFIG)
            val viewModel = SplashScreenViewModel(configRepository = configRepository)

            viewModel.navigation.test {
                // When
                viewModel.getConfig()

                // Then
                assertEquals(NavigationScreen.Onboarding.Start, awaitItem())
            }
        }

    @Test
    fun `Given viewmodel, When fetching config failed, Then navigate to error screen`() =
        runTest {
            // Given
            configRepository.setError(Exception("No Internet"))
            val viewModel = SplashScreenViewModel(configRepository = configRepository)

            viewModel.navigation.test {
                // When
                viewModel.getConfig()

                // Then
                assertEquals(NavigationScreen.Error.NoInternet, awaitItem())
            }
        }
}
