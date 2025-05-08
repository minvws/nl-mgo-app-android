package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.junit4.ComposeTestRule
import nl.rijksoverheid.mgo.feature.localisation.organizationList.manual.TEST_TAG_ORGANIZATION_SEARCH_CARD

internal class OrganizationSearchScreenRobot(private val composeTestRule: ComposeTestRule) {
  fun clickFirstSearchResult(block: DashboardScreenRobot.() -> Unit) {
    waitForListItems(composeTestRule = composeTestRule, listItemTestTag = TEST_TAG_ORGANIZATION_SEARCH_CARD) {
      clickFirstListItem(composeTestRule = composeTestRule, listItemTestTag = TEST_TAG_ORGANIZATION_SEARCH_CARD) {
        block(DashboardScreenRobot(composeTestRule))
      }
    }
  }
}
