package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import nl.rijksoverheid.mgo.component.mgo.MgoAlertDialogTestTag
import nl.rijksoverheid.mgo.feature.settings.home.SettingsHomeScreenTestTag

class SettingsHomeScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun clickResetAppButton(): SettingsHomeScreenRobot {
    composeTestRule
      .onNodeWithTag(SettingsHomeScreenTestTag.LIST)
      .performScrollToNode(hasTestTag(SettingsHomeScreenTestTag.RESET_APP_BUTTON))
    composeTestRule.onNodeWithTag(SettingsHomeScreenTestTag.RESET_APP_BUTTON).performClick()
    return this
  }

  fun clickResetAppDialogButton(): SettingsHomeScreenRobot {
    composeTestRule.onNodeWithTag(MgoAlertDialogTestTag.CONFIRM_BUTTON).performClick()
    return this
  }

  fun gotoIntroductionScreen(): IntroductionScreenRobot = IntroductionScreenRobot(composeTestRule)
}
