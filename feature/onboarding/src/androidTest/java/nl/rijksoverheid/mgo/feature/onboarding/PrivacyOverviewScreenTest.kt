package nl.rijksoverheid.mgo.feature.onboarding

import android.app.Activity
import android.app.Instrumentation
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import androidx.test.espresso.matcher.ViewMatchers.withId
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

internal class PrivacyOverviewScreenTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun privacyPolicyIsOpenedOnButtonClick() {
        composeTestRule.setContent {
            MgoTheme {
                PrivacyOverviewScreenContent(onClickNext = {})
            }
        }

        // Assert link is opened
        Intents.init()
        val intentMatcher =
            IntentMatchers.hasDataString(
                Matchers.equalTo(
                    "https://www.google.nl",
                ),
            )
        Intents.intending(intentMatcher).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        // When clicking the link
        // Using Espresso because compose does not seem to have a "openLinkWithText"
        Espresso.onView(withId(VIEW_ID_TEXT_WITH_LINK)).perform(ViewActions.openLinkWithText("privacyverklaring"))

        Intents.intended(intentMatcher)
        Intents.release()
    }
}
