package nl.rijksoverheid.mgo.feature.onboarding.proposition

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.click
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtons
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtonsTestTag
import nl.rijksoverheid.mgo.component.theme.MgoTheme
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows
import org.robolectric.annotation.Config

@Config(sdk = [34])
@RunWith(RobolectricTestRunner::class)
internal class PropositionScreenTest {
  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun privacyPolicyIsOpenedOnButtonClick() {
    val activity = Robolectric.buildActivity(ComponentActivity::class.java).setup().get()

    composeTestRule.setContent {
      MgoTheme {
        PropositionOverviewScreenContent(
          url = "https://www.google.nl",
          onNavigateBack = {},
          onClickNext = {},
        )
      }
    }

    composeTestRule
      .onNodeWithTag(MgoBottomButtonsTestTag.SECONDARY_BUTTON)
      .performClick()

    val shadowActivity = Shadows.shadowOf(activity)
    val startedIntent = shadowActivity.nextStartedActivity
    assertNotNull(startedIntent)
    assertEquals("https://www.google.nl", startedIntent.data.toString())
  }
}
