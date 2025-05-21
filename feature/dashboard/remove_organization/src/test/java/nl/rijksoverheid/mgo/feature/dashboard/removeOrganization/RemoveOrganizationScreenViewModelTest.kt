package nl.rijksoverheid.mgo.feature.dashboard.removeOrganization

import app.cash.turbine.turbineScope
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.component.mgo.snackbar.DefaultLocalDashboardSnackbarPresenter
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test

internal class RemoveOrganizationScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule =
    nl.rijksoverheid.mgo.framework.test.rules
      .MainDispatcherRule()

  private val organizationRepository =
    TestOrganizationRepository()

  @Test
  fun `Given a stored health care provider, When deleting that health care provider, ui is notified that provider is deleted`() =
    runTest {
      turbineScope {
        // Given
        val snackbarPresenter = DefaultLocalDashboardSnackbarPresenter()
        organizationRepository.setStoredProviders(providers = listOf(TEST_MGO_ORGANIZATION))
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
