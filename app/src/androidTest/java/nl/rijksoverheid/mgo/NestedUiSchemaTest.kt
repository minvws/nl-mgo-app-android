package nl.rijksoverheid.mgo

import androidx.compose.ui.test.junit4.createComposeRule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.framework.storage.file.EncryptedFileStore
import nl.rijksoverheid.mgo.robots.AddOrganizationScreenRobot
import nl.rijksoverheid.mgo.robots.HealthCategoriesScreenRobot
import nl.rijksoverheid.mgo.robots.LaunchAppRobot
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

/**
 * This test validates that when clicking through medical data, the nesting works as expected and shows the correct screens.
 */
@HiltAndroidTest
class NestedUiSchemaTest {
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
  fun testNestedUiSchema() {
    runTest {
      launchAppRobot.launchApp(
        skipOnboarding = true,
        pinCode = listOf(1, 2, 3, 4, 5),
        digidAuthenticated = true,
        skipPinCodeLogin = true,
      ) {
        HealthCategoriesScreenRobot(composeTestRule)
          .clickAddOrganizationButton()
          .gotoAddOrganizationScreen()
          .addOrganization("Kwalificatie Medmij: BGZ")
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

  private fun AddOrganizationScreenRobot.addOrganization(name: String): HealthCategoriesScreenRobot =
    this
      .setNameTextInput("test")
      .setCityTextInput("test")
      .clickSearchButton()
      .gotoOrganizationListScreen()
      .clickOrganization(name)
      .gotoHealthCareCategoriesScreen()
}
