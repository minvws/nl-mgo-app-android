package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import nl.rijksoverheid.mgo.component.mgo.MgoAlertDialogTestTag
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtonsTestTag

class PinCodeForgotScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun clickCreateNewAccount(): PinCodeForgotScreenRobot {
    composeTestRule
      .onNodeWithTag(MgoBottomButtonsTestTag.SECONDARY_BUTTON)
      .performClick()
    return this
  }

  fun clickCreateNewAccountDialogConfirmButton(): PinCodeForgotScreenRobot {
    composeTestRule
      .onNodeWithTag(MgoAlertDialogTestTag.CONFIRM_BUTTON)
      .performClick()
    return this
  }

  fun gotoPinCodeDeletedScreen(): PinCodeDeletedScreenRobot = PinCodeDeletedScreenRobot(composeTestRule)
}
