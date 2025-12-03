package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.launchActivity
import androidx.test.core.graphics.writeToTestStorage
import nl.rijksoverheid.mgo.robots.HealthCategoriesScreenRobot
import nl.rijksoverheid.mgo.rules.SetupAppRule
import org.junit.Rule
import org.junit.Test

/**
 * This test goes through the flow of adding data from a certain gegevensdienst (BGZ, GP, etc.).
 * After adding, it validates that a certain category is filled with data that we expect.
 * For example, when adding data from the vaccination gegevensdienst, we except the vaccination category to hold data.
 */
class AddOrganizationTest {
  @get:Rule
  val setupAppRule =
    SetupAppRule(
      skipOnboarding = true,
      pinCode = listOf(1, 2, 3, 4, 5),
      digidAuthenticated = true,
      skipPinCodeLogin = true,
    )

  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun testBgz() {
    launchActivity<MainActivity>().use {
      HealthCategoriesScreenRobot(composeTestRule)
        .also { takeScreenshot("1") }
        .clickAddOrganizationButton()
        .also { takeScreenshot("2") }
        .gotoAddOrganizationScreen()
        .also { takeScreenshot("3") }
        .setNameTextInput("test")
        .also { takeScreenshot("4") }
        .setCityTextInput("test")
        .also { takeScreenshot("5") }
        .clickSearchButton()
        .also { takeScreenshot("6") }
        .gotoOrganizationListScreen()
        .also { takeScreenshot("7") }
        .clickOrganization("Kwalificatie Medmij: GPDATA")
        .also { takeScreenshot("8") }
        .gotoHealthCareCategoriesScreen()
        .also { takeScreenshot("9") }
        .also { composeTestRule.waitForIdle() }
        .clickCategory("Uitslagen")
        .also { takeScreenshot("10") }
        .gotoHealthCategoryScreen()
        .also { composeTestRule.waitForIdle() }
        .also { takeScreenshot("11") }
        .assertCardsExists()
        .also { takeScreenshot("12") }
    }
  }

  private fun takeScreenshot(name: String) {
    val screenshot =
      androidx.test.core.app
        .takeScreenshot()
    screenshot.writeToTestStorage(name)
  }
}
