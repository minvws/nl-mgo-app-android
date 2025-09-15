package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import nl.rijksoverheid.mgo.feature.onboarding.introduction.IntroductionScreenTestTag

class IntroductionScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun assertIsDisplayed(): IntroductionScreenRobot {
    composeTestRule.waitForIdle()
    composeTestRule
      .onNodeWithTag(IntroductionScreenTestTag.SCREEN)
      .assertIsDisplayed()
    return this
  }
}
