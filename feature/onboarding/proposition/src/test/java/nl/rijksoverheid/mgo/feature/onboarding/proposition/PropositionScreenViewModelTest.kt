package nl.rijksoverheid.mgo.feature.onboarding.proposition

import nl.rijksoverheid.mgo.data.onboarding.TestSetHasSeenOnboarding
import nl.rijksoverheid.mgo.framework.environment.AppFlavor
import nl.rijksoverheid.mgo.framework.environment.AppInfo
import org.junit.Assert.assertTrue
import org.junit.Test

class PropositionScreenViewModelTest {
    private val setHasSeenOnboarding = TestSetHasSeenOnboarding()

    @Test
    fun `Given ViewModel, When setHasSeenOnboarding is called, Then use case is called`() {
        // Given
        val viewModel =
            PropositionScreenViewModel(
                appInfo = AppInfo(1, AppFlavor.PROD),
                setHasSeenOnboarding = setHasSeenOnboarding,
            )

        // When
        viewModel.setHasSeenOnboarding()

        // Then
        assertTrue(setHasSeenOnboarding.get())
    }
}
