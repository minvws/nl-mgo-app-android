package nl.rijksoverheid.mgo.feature.settings.home

import app.cash.turbine.test
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.organization.OrganizationRepository
import nl.rijksoverheid.mgo.data.organization.createOrganizationRepositoryForJvm
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

internal class SettingsHomeScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  private val environmentRepository = mockk<EnvironmentRepository>()

  private lateinit var organizationRepository: OrganizationRepository

  @Before
  fun setup() =
    runTest {
      organizationRepository = createOrganizationRepositoryForJvm()
    }

  @Test
  fun testViewState() =
    runTest {
      // Given: Environment is set to tst
      every { environmentRepository.getEnvironment() } answers { Environment.Tst(1, "1") }

      // Given: View model
      val viewModel =
        SettingsHomeScreenViewModel(
          environmentRepository = environmentRepository,
        )

      // Then: Show advanced screen is true
      viewModel.viewState.test {
        val viewState = awaitItem()
        assertEquals(true, viewState.showAdvancedScreen)
      }
    }
}
