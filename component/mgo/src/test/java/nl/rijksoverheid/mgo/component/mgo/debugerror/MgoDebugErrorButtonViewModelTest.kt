package nl.rijksoverheid.mgo.component.mgo.debugerror

import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.TestEnvironmentRepository
import org.junit.Assert.assertEquals
import org.junit.Test

internal class MgoDebugErrorButtonViewModelTest {
    @Test
    fun `Given production app flavor, When calling showButton, Then return false`() {
        // Given
        val environmentRepository = TestEnvironmentRepository()
        environmentRepository.setEnvironment(environment = Environment.Prod(versionCode = 1))

        // When
        val viewModel =
            MgoDebugErrorButtonViewModel(
                environmentRepository = environmentRepository,
            )
        val showButton = viewModel.showButton

        // Then
        assertEquals(false, showButton)
    }

    @Test
    fun `Given not production app flavor, When calling showButton, Then return true`() {
        // Given
        val environmentRepository = TestEnvironmentRepository()
        environmentRepository.setEnvironment(environment = Environment.Acc(versionCode = 1))

        // When
        val viewModel =
            MgoDebugErrorButtonViewModel(
                environmentRepository,
            )
        val showButton = viewModel.showButton

        // Then
        assertEquals(true, showButton)
    }
}
