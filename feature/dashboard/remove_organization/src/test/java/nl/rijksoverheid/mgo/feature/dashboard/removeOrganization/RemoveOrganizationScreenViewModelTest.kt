package nl.rijksoverheid.mgo.feature.dashboard.removeOrganization

import app.cash.turbine.turbineScope
import nl.rijksoverheid.mgo.component.snackbar.DefaultSnackBarRepository
import nl.rijksoverheid.mgo.data.localisation.models.TEST_MGO_ORGANIZATION
import nl.rijksoverheid.mgo.framework.copy.R
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import nl.rijksoverheid.mgo.localisation.TestOrganizationRepository
import org.junit.Assert.assertEquals
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
                val snackBarRepository = DefaultSnackBarRepository()
                organizationRepository.setStoredProviders(providers = listOf(TEST_MGO_ORGANIZATION))
                val viewModel =
                    RemoveOrganizationScreenViewModel(
                        organizationRepository = organizationRepository,
                        snackBarRepository = snackBarRepository,
                    )
                val turbine1 = viewModel.providerDeleted.testIn(backgroundScope)
                val turbine2 = snackBarRepository.get().testIn(backgroundScope)

                // When
                viewModel.delete(TEST_MGO_ORGANIZATION.id)

                // Then
                assertEquals(Unit, turbine1.awaitItem())
                assertEquals(R.string.toast_organization_removed_heading, turbine2.awaitItem().title)
            }
        }
}
