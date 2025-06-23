package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import nl.rijksoverheid.mgo.feature.dashboard.bottombar.BottomBarItem
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

  fun selectOrganizationsTab(): DashboardBottomBarScreenRobot {
    composeTestRule
      .onNodeWithTag(BottomBarItem.ORGANIZATIONS.testTag)
      .performClick()
    return this
  }

  fun selectSettingsTab(): DashboardBottomBarScreenRobot {
    composeTestRule
      .onNodeWithTag(BottomBarItem.SETTINGS.testTag)
      .performClick()
    return this
  }

  fun gotoOrganizationsScreen(): OrganizationsScreenRobot = OrganizationsScreenRobot(composeTestRule)

  fun gotoSettingsHomeScreen(): SettingsHomeScreenRobot = SettingsHomeScreenRobot(composeTestRule)
}
