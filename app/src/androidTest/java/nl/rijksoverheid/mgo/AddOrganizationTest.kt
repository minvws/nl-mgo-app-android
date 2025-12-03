package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.launchActivity
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
        .clickAddOrganizationButton()
        .gotoAddOrganizationScreen()
        .setNameTextInput("test")
        .setCityTextInput("test")
        .clickSearchButton()
        .gotoOrganizationListScreen()
        .clickOrganization("Kwalificatie Medmij: BGZ")
        .gotoHealthCareCategoriesScreen()
        .clickCategory("Uitslagen")
        .gotoHealthCategoryScreen()
        .assertCardsExists()
    }
  }

  @Test
  fun testGp() {
    launchActivity<MainActivity>().use {
      HealthCategoriesScreenRobot(composeTestRule)
        .clickAddOrganizationButton()
        .gotoAddOrganizationScreen()
        .setNameTextInput("test")
        .setCityTextInput("test")
        .clickSearchButton()
        .gotoOrganizationListScreen()
        .clickOrganization("Kwalificatie Medmij: GPDATA")
        .gotoHealthCareCategoriesScreen()
        .clickCategory("Uitslagen")
        .gotoHealthCategoryScreen()
        .assertCardsExists()
    }
  }

  @Test
  fun testDoc() {
    launchActivity<MainActivity>().use {
      HealthCategoriesScreenRobot(composeTestRule)
        .clickAddOrganizationButton()
        .gotoAddOrganizationScreen()
        .setNameTextInput("test")
        .setCityTextInput("test")
        .clickSearchButton()
        .gotoOrganizationListScreen()
        .clickOrganization("Kwalificatie Medmij: PDFA")
        .gotoHealthCareCategoriesScreen()
        .clickCategory("Documenten")
        .gotoHealthCategoryScreen()
        .assertCardsExists()
    }
  }

  @Test
  fun testVaccinations() {
    launchActivity<MainActivity>().use {
      HealthCategoriesScreenRobot(composeTestRule)
        .clickAddOrganizationButton()
        .gotoAddOrganizationScreen()
        .setNameTextInput("test")
        .setCityTextInput("test")
        .clickSearchButton()
        .gotoOrganizationListScreen()
        .clickOrganization("Kwalificatie Medmij: VACCINATION_IMMUNIZATION")
        .gotoHealthCareCategoriesScreen()
        .clickCategory("Vaccinaties")
        .gotoHealthCategoryScreen()
        .assertCardsExists()
    }
  }
}
