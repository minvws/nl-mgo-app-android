package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.launchActivity
import nl.rijksoverheid.mgo.robots.DashboardBottomBarScreenRobot
import nl.rijksoverheid.mgo.rules.SetupAppRule
import org.junit.Rule
import org.junit.Test

/**
 * This test validates that when a second organization is added, it is correctly added in the organizations tab.
 */
class AddSecondOrganizationTest {
  @get:Rule
  val setupAppRule =
    SetupAppRule(
      skipOnboarding = true,
      digidAuthenticated = true,
      skipDigidLogin = true,
    )

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun testAddSecondOrganization() {
    launchActivity<MainActivity>().use {
      DashboardBottomBarScreenRobot(composeTestRule)
        .selectOrganizationsTab()
        .gotoOrganizationsScreen()
        .clickAddOrganizationButtonEmptyState()
        .gotoManualLocalisationScreen()
        .setSearchInput("testtest")
        .addOrganization("Kwalificatie Medmij: BGZ")
        .gotoOrganizationsScreen()
        .assertAddedOrganization(amount = 1)
        .clickAddOrganizationButton()
        .gotoManualLocalisationScreen()
        .setSearchInput("testtest")
        .addOrganization("Kwalificatie Medmij: GPDATA")
        .gotoOrganizationsScreen()
        .assertAddedOrganization(amount = 2)
    }
  }
}
