package nl.rijksoverheid.mgo.feature.localisation.addOrganization

import app.cash.turbine.test
import app.cash.turbine.turbineScope
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.test.rules.MainDispatcherRule
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

internal class AddOrganizationScreenViewModelTest {
  @get:Rule
  val mainDispatcherRule = MainDispatcherRule()

  @Test
  fun `Given no name and city have been entered, When calling validate, Then emit error`() =
    runTest {
      // Given
      val viewModel = AddOrganizationScreenViewModel()

      // When
      viewModel.validate()

      // Then
      viewModel.viewState.test {
        val expectedViewState =
          AddOrganizationScreenViewState(
            name = "",
            nameError = CopyR.string.add_organization_error_missing_name,
            city = "",
            cityError = CopyR.string.add_organization_error_missing_city,
          )
        assertEquals(expectedViewState, awaitItem())
      }
    }

  @Test
  fun `Given no name has been entered, When calling validate, Then emit error`() =
    runTest {
      // Given
      val viewModel = AddOrganizationScreenViewModel()

      // When
      viewModel.setCity("Roermond")
      viewModel.validate()

      // Then
      viewModel.viewState.test {
        val expectedViewState =
          AddOrganizationScreenViewState(
            name = "",
            nameError = CopyR.string.add_organization_error_missing_name,
            city = "Roermond",
            cityError = null,
          )
        assertEquals(expectedViewState, awaitItem())
      }
    }

  @Test
  fun `Given no city has been entered, When calling validate, Then emit error`() =
    runTest {
      // Given
      val viewModel = AddOrganizationScreenViewModel()

      // When
      viewModel.setName("Tandarts")
      viewModel.validate()

      // Then
      viewModel.viewState.test {
        val expectedViewState =
          AddOrganizationScreenViewState(
            name = "Tandarts",
            nameError = null,
            city = "",
            cityError = CopyR.string.add_organization_error_missing_city,
          )
        assertEquals(expectedViewState, awaitItem())
      }
    }

  @Test
  fun `Given name and city have been entered, When calling validate, Then navigate`() =
    runTest {
      // Given
      val viewModel = AddOrganizationScreenViewModel()

      // When
      viewModel.setName("Tandarts")
      viewModel.setCity("Roermond")

      turbineScope {
        val viewStateFlow = viewModel.viewState.testIn(backgroundScope)
        val navigationFlow = viewModel.navigation.testIn(backgroundScope)
        viewModel.validate()

        // Then
        val expectedViewState =
          AddOrganizationScreenViewState(
            name = "Tandarts",
            nameError = null,
            city = "Roermond",
            cityError = null,
          )
        assertEquals(expectedViewState, viewStateFlow.awaitItem())
        assertEquals(Unit, navigationFlow.awaitItem())
      }
    }
}
