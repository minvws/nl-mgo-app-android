package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import nl.rijksoverheid.mgo.feature.pincode.create.PinCodeCreateScreenTestTag

class PinCodeCreateScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun assertIsDisplayed(): PinCodeCreateScreenRobot {
    composeTestRule.waitForIdle()
    composeTestRule
      .onNodeWithTag(PinCodeCreateScreenTestTag.SCREEN)
      .assertIsDisplayed()
    return this
  }
}
