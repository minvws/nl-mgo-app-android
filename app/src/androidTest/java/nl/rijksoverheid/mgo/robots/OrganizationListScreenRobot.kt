package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import nl.rijksoverheid.mgo.feature.localisation.organizationList.manual.OrganizationListManualCardTestTag
import nl.rijksoverheid.mgo.feature.localisation.organizationList.manual.OrganizationListManualScreenTestTag
import nl.rijksoverheid.mgo.utils.waitForListItems

class OrganizationListScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun clickOrganization(name: String): OrganizationListScreenRobot {
    composeTestRule.waitForListItems(OrganizationListManualCardTestTag.CARD)
    composeTestRule.onNodeWithTag(OrganizationListManualScreenTestTag.LIST).performScrollToNode(hasText(name))
    composeTestRule.onNodeWithText(name).performClick()
    return this
  }

  fun gotoHealthCareCategoriesScreen(): HealthCategoriesScreenRobot = HealthCategoriesScreenRobot(composeTestRule)

  fun gotoOrganizationsScreen(): OrganizationsScreenRobot = OrganizationsScreenRobot(composeTestRule)
}
