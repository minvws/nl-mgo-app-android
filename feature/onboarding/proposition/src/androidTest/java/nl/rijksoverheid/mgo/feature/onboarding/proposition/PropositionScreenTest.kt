package nl.rijksoverheid.mgo.feature.onboarding.proposition

import android.app.Activity
import android.app.Instrumentation
import androidx.compose.ui.test.click
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.performTouchInput
import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import org.hamcrest.Matchers
import org.junit.Rule
import org.junit.Test

internal class PropositionScreenTest {
  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun privacyPolicyIsOpenedOnButtonClick() {
    composeTestRule.setContent {
      MgoTheme {
        PropositionOverviewScreenContent(
          url = "https://www.google.nl",
          onNavigateBack = {},
          onClickNext = {},
        )
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
    // Verify fragile test because compose does not have a ViewActions.openLinkWithText
    composeTestRule.onNode(hasText("privacyverklaring", substring = true))
      .performTouchInput { click(percentOffset(0.1f, 0f)) }

    Intents.intended(intentMatcher)
    Intents.release()
  }
}
