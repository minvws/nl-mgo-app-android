package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.robots.AddOrganizationScreenRobot
import nl.rijksoverheid.mgo.robots.DashboardBottomBarScreenRobot
import nl.rijksoverheid.mgo.robots.OrganizationsScreenRobot
import nl.rijksoverheid.mgo.rules.LaunchAppRule
import org.junit.Rule
import org.junit.Test

/**
 * This test validates that when a organization is removed, a snackbar is shown and the organization is removed.
 */
class RemoveOrganizationTest {
  @get:Rule
  val launchAppRule = LaunchAppRule()

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun testRemoveOrganization() {
    runTest {
      launchAppRule.launchApp(
        skipOnboarding = true,
        pinCode = listOf(1, 2, 3, 4, 5),
        digidAuthenticated = true,
        skipPinCodeLogin = true,
      ) {
        DashboardBottomBarScreenRobot(composeTestRule)
          .selectOrganizationsTab()
          .gotoOrganizationsScreen()
          .clickAddOrganizationButtonEmptyState()
          .gotoAddOrganizationScreen()
          .addOrganization("Kwalificatie Medmij: BGZ")
          .assertAddedOrganization(amount = 1)
          .clickFirstOrganization()
          .gotoHealthCategoriesScreen()
          .clickDeleteOrganizationButton()
          .gotoRemoveOrganizationsScreen()
          .clickRemoveButton()
          .gotoOrganizationScreen()
          .isDisplayedOrganizationRemovedSnackbar()
          .assertNoAddedOrganizations()
      }
    }
  }

  private fun AddOrganizationScreenRobot.addOrganization(name: String): OrganizationsScreenRobot =
    this
      .setNameTextInput("test")
      .setCityTextInput("test")
      .clickSearchButton()
      .gotoOrganizationListScreen()
      .clickOrganization(name)
      .gotoOrganizationsScreen()
}
