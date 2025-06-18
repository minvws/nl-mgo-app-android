package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtonsTestTag

class PinCodeDeletedScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun clickConfirmButton(): PinCodeDeletedScreenRobot {
    composeTestRule
      .onNodeWithTag(MgoBottomButtonsTestTag.PRIMARY_BUTTON)
      .performClick()
    return this
  }

  fun gotoPinCodeCreateScreen(): PinCodeCreateScreenRobot = PinCodeCreateScreenRobot(composeTestRule)
}
