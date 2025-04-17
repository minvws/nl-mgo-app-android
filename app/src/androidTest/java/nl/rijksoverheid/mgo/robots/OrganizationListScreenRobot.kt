package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import nl.rijksoverheid.mgo.component.theme.TEST_TAG_COLUMN_WITH_BUTTON_PRIMARY_BUTTON

internal class OrganizationListScreenRobot(private val composeTestRule: ComposeTestRule) {
  internal fun pressDoneButton(block: DashboardScreenRobot.() -> Unit) {
    composeTestRule.onNodeWithTag(TEST_TAG_COLUMN_WITH_BUTTON_PRIMARY_BUTTON).performClick()
    block(DashboardScreenRobot(composeTestRule))
  }
}
