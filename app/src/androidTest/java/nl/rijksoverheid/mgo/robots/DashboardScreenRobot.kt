package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import nl.rijksoverheid.mgo.component.theme.TEST_TAG_COLUMN_WITH_BUTTON_PRIMARY_BUTTON
import nl.rijksoverheid.mgo.feature.config.TEST_TAG_UPDATE_REQUIRED_TITLE

internal class DashboardScreenRobot(private val composeTestRule: ComposeTestRule) {
//    internal fun assertNoOrganizations() {
//        assertNoListItems(composeTestRule = composeTestRule, testTag = TEST_TAG_OVERVIEW_ORGANIZATION_CARD)
//    }
//
//    internal fun assertOneOrganization() {
//        assertOneListItem(composeTestRule = composeTestRule, testTag = TEST_TAG_OVERVIEW_ORGANIZATION_CARD)
//    }

  internal fun assertUpdateRequiredScreenVisible() {
    composeTestRule.onNodeWithTag(TEST_TAG_UPDATE_REQUIRED_TITLE).assertExists()
  }

  internal fun assertUpdateRequiredScreenNotVisible() {
    composeTestRule.onNodeWithTag(TEST_TAG_UPDATE_REQUIRED_TITLE).assertDoesNotExist()
  }

  internal fun clickLocalisationButton(block: AddOrganizationScreenRobot.() -> Unit) {
    composeTestRule.onNodeWithTag(TEST_TAG_COLUMN_WITH_BUTTON_PRIMARY_BUTTON).performClick()
    block(AddOrganizationScreenRobot(composeTestRule))
  }
}
