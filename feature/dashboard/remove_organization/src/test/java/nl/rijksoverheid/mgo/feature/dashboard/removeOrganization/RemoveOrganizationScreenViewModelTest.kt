package nl.rijksoverheid.mgo.feature.dashboard.removeOrganization

import app.cash.turbine.turbineScope
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.mgo.snackbar.DefaultLocalDashboardSnackbarPresenter
import nl.rijksoverheid.mgo.component.organization.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.bytearray.MemoryMgoByteArrayStorage
import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class RemoveOrganizationScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule =
    nl.rijksoverheid.mgo.framework.test.rules
      .MainDispatcherRule()

  private val organizationRepository = OrganizationRepository(okHttpClient = OkHttpClient(), baseUrl = "", mgoByteArrayStorage = MemoryMgoByteArrayStorage())

  @Before
  fun setup() =
    runTest {
      organizationRepository.deleteAll()
    }

  @Test
  fun `Given a stored health care provider, When deleting that health care provider, ui is notified that provider is deleted`() =
    runTest {
      turbineScope {
        // Given
        val snackbarPresenter = DefaultLocalDashboardSnackbarPresenter()
        organizationRepository.save(TEST_MGO_ORGANIZATION)
        val viewModel =
          RemoveOrganizationScreenViewModel(
            organizationRepository = organizationRepository,
          )
        val turbine1 = viewModel.providerDeleted.testIn(backgroundScope)
        val turbine2 = snackbarPresenter.snackbarVisuals.testIn(backgroundScope)

        // When
        viewModel.delete(snackbarPresenter, TEST_MGO_ORGANIZATION.id)

        // Then
        assertEquals(Unit, turbine1.awaitItem())
        assertNotNull(turbine2.awaitItem())
      }
    }
}
