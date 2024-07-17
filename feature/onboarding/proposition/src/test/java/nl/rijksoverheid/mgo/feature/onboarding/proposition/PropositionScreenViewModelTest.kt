package nl.rijksoverheid.mgo.feature.onboarding.proposition

import nl.rijksoverheid.mgo.data.onboarding.TestSetHasSeenOnboarding
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.TestEnvironmentRepository
import org.junit.Assert.assertTrue
import org.junit.Test

class PropositionScreenViewModelTest {
    private val setHasSeenOnboarding = TestSetHasSeenOnboarding()

    @Test
    fun `Given ViewModel, When setHasSeenOnboarding is called, Then use case is called`() {
        // Given
        val environmentRepository = TestEnvironmentRepository()
        environmentRepository.setEnvironment(Environment.Prod(1))
        val viewModel =
            PropositionScreenViewModel(
                environmentRepository = environmentRepository,
                setHasSeenOnboarding = setHasSeenOnboarding,
            )

        // When
        viewModel.setHasSeenOnboarding()

        // Then
        assertTrue(setHasSeenOnboarding.get())
    }
}
