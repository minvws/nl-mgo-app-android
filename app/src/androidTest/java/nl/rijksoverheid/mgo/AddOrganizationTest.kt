package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.ApplicationProvider
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.robots.HealthCategoriesScreenRobot
import nl.rijksoverheid.mgo.robots.LaunchAppRobot
import org.junit.Rule
import org.junit.Test

/**
 * This test goes through the flow of adding data from a certain gegevensdienst (BGZ, GP, etc.).
 * After adding, it validates that a certain category is filled with data that we expect.
 * For example, when adding data from the vaccination gegevensdienst, we except the vaccination category to hold data.
 */
class AddOrganizationTest {
  @get:Rule
  val composeTestRule = createComposeRule()

  @Test
  fun testBgz() =
    runTest {
      val mainApplication = ApplicationProvider.getApplicationContext<MainApplication>()
      val launchAppRobot = LaunchAppRobot(mainApplication)

      launchAppRobot.launchApp(
        skipOnboarding = true,
        pinCode = listOf(1, 2, 3, 4, 5),
        digidAuthenticated = true,
        skipPinCodeLogin = true,
      ) {
        HealthCategoriesScreenRobot(composeTestRule)
          .clickAddOrganizationButton()
          .gotoAddOrganizationScreen()
          .setNameTextInput("test")
          .setCityTextInput("test")
          .clickSearchButton()
          .gotoOrganizationListScreen()
          .clickOrganization("Kwalificatie Medmij: BGZ")
          .gotoHealthCareCategoriesScreen()
          .clickCategory("Medische klachten")
          .gotoHealthCategoryScreen()
          .assertCardsExists()
      }
    }

  @Test
  fun testGp() =
    runTest {
      val mainApplication = ApplicationProvider.getApplicationContext<MainApplication>()
      val launchAppRobot = LaunchAppRobot(mainApplication)

      launchAppRobot.launchApp(
        skipOnboarding = true,
        pinCode = listOf(1, 2, 3, 4, 5),
        digidAuthenticated = true,
        skipPinCodeLogin = true,
      ) {
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
  fun testDoc() =
    runTest {
      val mainApplication = ApplicationProvider.getApplicationContext<MainApplication>()
      val launchAppRobot = LaunchAppRobot(mainApplication)

      launchAppRobot.launchApp(
        skipOnboarding = true,
        pinCode = listOf(1, 2, 3, 4, 5),
        digidAuthenticated = true,
        skipPinCodeLogin = true,
      ) {
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
  fun testVaccinations() =
    runTest {
      val mainApplication = ApplicationProvider.getApplicationContext<MainApplication>()
      val launchAppRobot = LaunchAppRobot(mainApplication)

      launchAppRobot.launchApp(
        skipOnboarding = true,
        pinCode = listOf(1, 2, 3, 4, 5),
        digidAuthenticated = true,
        skipPinCodeLogin = true,
      ) {
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
