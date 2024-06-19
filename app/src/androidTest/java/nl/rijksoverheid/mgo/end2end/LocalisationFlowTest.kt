package nl.rijksoverheid.mgo.end2end

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import dagger.hilt.android.testing.HiltAndroidTest
import nl.rijksoverheid.mgo.MainActivityTest
import org.junit.Test
import org.junit.runner.RunWith

@HiltAndroidTest
@RunWith(AndroidJUnit4::class)
@LargeTest
internal class LocalisationFlowTest : MainActivityTest() {
    @Test
    fun providerAddedAfterLocalisationFlow() {
        launchAppSkipOnboarding {
            // On fresh app launch, no providers are shown.
            assertNoProviders()

            // Enter localisation flow.
            clickLocalisationButton {
                inputName("Tandarts")
                inputCity("Breda")
                pressSearchButton {
                    clickFirstSearchResult {
                        assertOneProvider()
                    }
                }
            }
        }
    }
}
