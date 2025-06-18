package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import nl.rijksoverheid.mgo.component.pincode.PinCodeWithKeyboardTestTag
import nl.rijksoverheid.mgo.component.pincode.keyboard.KeyboardItemNumberTestTag

class PinCodeLoginScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun clickForgotPinCodeButton(): PinCodeForgotScreenRobot {
    composeTestRule
      .onNodeWithTag(PinCodeWithKeyboardTestTag.HINT)
      .performClick()
    return PinCodeForgotScreenRobot(composeTestRule)
  }

  fun clickKeyboardNumber(number: Int): PinCodeLoginScreenRobot {
    composeTestRule
      .onNodeWithTag(KeyboardItemNumberTestTag.button(number))
      .performClick()
    return this
  }

  fun clickKeyboardNumbers(numbers: List<Int>): PinCodeLoginScreenRobot {
    for (number in numbers) {
      clickKeyboardNumber(number)
    }
    return this
  }

  fun gotoDashboardBottomBarScreen(): DashboardBottomBarScreenRobot = DashboardBottomBarScreenRobot(composeTestRule)
}
