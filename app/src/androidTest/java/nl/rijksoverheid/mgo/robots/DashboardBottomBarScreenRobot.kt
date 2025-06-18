package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import nl.rijksoverheid.mgo.feature.dashboard.bottombar.DashboardBottomBarScreenTestTag

class DashboardBottomBarScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun assertIsDisplayed(): DashboardBottomBarScreenRobot {
    composeTestRule
      .onNodeWithTag(DashboardBottomBarScreenTestTag.SCREEN)
      .assertIsDisplayed()
    return this
  }
}
