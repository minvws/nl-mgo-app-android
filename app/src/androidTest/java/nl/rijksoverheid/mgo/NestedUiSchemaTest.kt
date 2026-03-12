package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.core.app.launchActivity
import nl.rijksoverheid.mgo.robots.HealthCategoriesScreenRobot
import nl.rijksoverheid.mgo.rules.SetupAppRule
import org.junit.Rule
import org.junit.Test

/**
 * This test validates that when clicking through medical data, the nesting works as expected and shows the correct screens.
 */
class NestedUiSchemaTest {
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
  fun testNestedUiSchema() {
    launchActivity<MainActivity>().use {
      HealthCategoriesScreenRobot(composeTestRule)
        .clickAddOrganizationButton()
        .gotoManualLocalisationScreen()
        .setSearchInput("testtest")
        .addOrganization("Kwalificatie Medmij: BGZ")
        .gotoHealthCategoriesScreen()
        .clickCategory("Medicijnen")
        .gotoHealthCategoryScreen()
        .clickFirstListItem()
        .gotoUiSchemaScreen()
        .clickRow("Bekijk alle medicijngegevens")
        .clickRow("Informatiebron")
        .isDisplayedBottomSheet()
    }
  }
}
