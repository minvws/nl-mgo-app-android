package nl.rijksoverheid.mgo.feature.config

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import nl.rijksoverheid.mgo.component.theme.TEST_TAG_COLUMN_WITH_BUTTON_BUTTON
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

internal class UpdateRequiredScreenTest {
    @get:Rule(order = 1)
    val composeTestRule = createComposeRule()

    @Test
    fun playStorePageIsOpenedOnButtonClick() {
        composeTestRule.setContent {
            MgoTheme {
                UpdateRequiredScreen()
            }
        }

        // Assert link is opened
        Intents.init()
        val intentMatcher =
            IntentMatchers.hasDataString(
                Matchers.equalTo(
                    "https://play.google.com/store/apps/details?id=${ApplicationProvider.getApplicationContext<Context>().packageName}",
                ),
            )
        Intents.intending(intentMatcher).respondWith(Instrumentation.ActivityResult(Activity.RESULT_OK, null))

        // When clicking the button
        composeTestRule.onNodeWithTag(TEST_TAG_COLUMN_WITH_BUTTON_BUTTON).performClick()

        Intents.intended(intentMatcher)
        Intents.release()
    }
}
