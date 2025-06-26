package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import nl.rijksoverheid.mgo.feature.localisation.organizationList.manual.OrganizationListManualCardTestTag
import nl.rijksoverheid.mgo.utils.waitForListItems

class OrganizationListScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun clickOrganization(name: String): OrganizationListScreenRobot {
    composeTestRule.waitForListItems(OrganizationListManualCardTestTag.CARD)
    composeTestRule.onNodeWithText(name).performClick()
    return this
  }

  fun gotoHealthCareCategoriesScreen(): HealthCategoriesScreenRobot = HealthCategoriesScreenRobot(composeTestRule)

  fun gotoOrganizationsScreen(): OrganizationsScreenRobot = OrganizationsScreenRobot(composeTestRule)
}
