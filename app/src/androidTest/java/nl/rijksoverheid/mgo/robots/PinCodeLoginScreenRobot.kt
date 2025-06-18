package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import nl.rijksoverheid.mgo.component.pincode.keyboard.KeyboardItemNumberTestTag

class PinCodeLoginScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun pressKeyboardNumber(number: Int): PinCodeLoginScreenRobot {
    composeTestRule
      .onNodeWithTag(KeyboardItemNumberTestTag.button(number))
      .performClick()
    return this
  }

  fun pressKeyboardNumbers(numbers: List<Int>): PinCodeLoginScreenRobot {
    for (number in numbers) {
      pressKeyboardNumber(number)
    }
    return this
  }

  fun assertDashboardIsDisplayed(): DashboardBottomBarScreenRobot =
    composeTestRule.waitForIdle().run {
      DashboardBottomBarScreenRobot(composeTestRule)
        .assertIsDisplayed()
    }
}
