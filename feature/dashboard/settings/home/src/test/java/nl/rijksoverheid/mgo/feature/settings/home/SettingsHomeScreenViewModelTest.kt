package nl.rijksoverheid.mgo.feature.settings.home

import app.cash.turbine.test
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.pincode.biometric.TestDeviceHasBiometric
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class SettingsHomeScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val organizationRepository = OrganizationRepository(okHttpClient = OkHttpClient(), baseUrl = "", mgoByteArrayStorage = MemoryMgoByteArrayStorage())

  @Before
  fun setup() =
    runTest {
      organizationRepository.deleteAll()
    }

  @Test
  fun testViewState() =
    runTest {
      // Given: View model
      val viewModel =
        SettingsHomeScreenViewModel(
          deviceHasBiometric = TestDeviceHasBiometric(true),
          isDebug = true,
        )

      // Then: App theme is system and device has biometric is true
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertEquals(true, viewState.deviceHasBiometric)
      }
    }
}
