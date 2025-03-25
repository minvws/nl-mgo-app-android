package nl.rijksoverheid.mgo.feature.settings.home

import app.cash.turbine.test
import nl.rijksoverheid.mgo.component.theme.theme.AppTheme
import nl.rijksoverheid.mgo.data.pincode.biometric.TestLoginWithBiometricEnabled
import nl.rijksoverheid.mgo.framework.storage.keyvalue.TestKeyValueStore
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class SettingsHomeScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun testViewState() =
        runTest {
            // Given: View model
            val viewModel =
                SettingsHomeScreenViewModel(
                    keyValueStore = TestKeyValueStore(),
                    loginWithBiometricEnabled = TestLoginWithBiometricEnabled(true),
                )

            // Then: App theme is system and login with biometric is enabled
            viewModel.viewState.test {
                val viewState = awaitItem()
                assertEquals(AppTheme.SYSTEM, viewState.appTheme)
                assertEquals(true, viewState.biometricEnabled)
            }
        }
}
