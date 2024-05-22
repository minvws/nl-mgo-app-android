package nl.rijksoverheid.mgo.feature.onboarding.privacyoverview

import nl.rijksoverheid.mgo.data.onboarding.TestSetHasSeenOnboarding
import org.junit.Assert.assertTrue
import org.junit.Test

class PrivacyOverviewScreenViewModelTest {
    private val setHasSeenOnboarding = TestSetHasSeenOnboarding()

    @Test
    fun `Given ViewModel, When setHasSeenOnboarding is called, Then use case is called`() {
        // Given
        val viewModel = PrivacyOverviewScreenViewModel(setHasSeenOnboarding)

        // When
        viewModel.setHasSeenOnboarding()

        // Then
        assertTrue(setHasSeenOnboarding.get())
    }
}
