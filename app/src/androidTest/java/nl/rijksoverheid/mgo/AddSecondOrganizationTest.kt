package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.storage.file.EncryptedFileStore
import nl.rijksoverheid.mgo.robots.AddOrganizationScreenRobot
import nl.rijksoverheid.mgo.robots.DashboardBottomBarScreenRobot
import nl.rijksoverheid.mgo.robots.LaunchAppRobot
import nl.rijksoverheid.mgo.robots.OrganizationsScreenRobot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 * This test validates that when a second organization is added, it is correctly added in the organizations tab.
 */
@HiltAndroidTest
class AddSecondOrganizationTest {
  @get:Rule
  var hiltRule = HiltAndroidRule(this)

  @get:Rule
  val composeTestRule = createComposeRule()

  @Inject
  lateinit var launchAppRobot: LaunchAppRobot

  @Inject
  lateinit var encryptedFileStore: EncryptedFileStore

  @Before
  fun setup() =
    runTest {
      hiltRule.inject()
      encryptedFileStore.deleteAll()
    }

  @Test
  fun testAddSecondOrganization() {
    runTest {
      launchAppRobot.launchApp(
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
          .clickAddOrganizationButton()
          .gotoAddOrganizationScreen()
          .addOrganization("Kwalificatie Medmij: GPDATA")
          .assertAddedOrganization(amount = 2)
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
