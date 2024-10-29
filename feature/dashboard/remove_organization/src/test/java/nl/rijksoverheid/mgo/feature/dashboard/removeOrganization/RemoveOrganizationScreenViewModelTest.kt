package nl.rijksoverheid.mgo.feature.dashboard.removeOrganization

import app.cash.turbine.turbineScope
import nl.rijksoverheid.mgo.component.theme.snackbar.DefaultLocalSnackbarPresenter
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

internal class RemoveOrganizationScreenViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val organizationRepository =
        TestOrganizationRepository()

    @Test
    fun `Given a stored health care provider, When deleting that health care provider, ui is notified that provider is deleted`() =
        runTest {
            turbineScope {
                // Given
                val snackbarPresenter = DefaultLocalSnackbarPresenter()
                organizationRepository.setStoredProviders(providers = listOf(TEST_MGO_ORGANIZATION))
                val viewModel =
                    RemoveOrganizationScreenViewModel(
                        organizationRepository = organizationRepository,
                    )
                val turbine1 = viewModel.providerDeleted.testIn(backgroundScope)

                // When
                viewModel.delete(snackbarPresenter, TEST_MGO_ORGANIZATION.id)

                // Then
                assertEquals(Unit, turbine1.awaitItem())
                assertNotNull(snackbarPresenter.consume())
            }
        }
}
