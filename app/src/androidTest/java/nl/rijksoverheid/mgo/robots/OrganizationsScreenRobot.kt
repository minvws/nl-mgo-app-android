package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtonsTestTag
import nl.rijksoverheid.mgo.component.mgo.snackbar.MgoSnackbarTestTag
import nl.rijksoverheid.mgo.feature.dashboard.organizations.OrganizationsScreenTestTag
import nl.rijksoverheid.mgo.utils.assertListItems
import nl.rijksoverheid.mgo.utils.waitForListItems

class OrganizationsScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun clickAddOrganizationButtonEmptyState(): OrganizationsScreenRobot {
    composeTestRule
      .onNodeWithTag(MgoBottomButtonsTestTag.PRIMARY_BUTTON)
      .performClick()
    return this
  }

  fun clickAddOrganizationButton(): OrganizationsScreenRobot {
    composeTestRule
      .onNodeWithTag(OrganizationsScreenTestTag.ADD_ORGANIZATION_BUTTON)
      .performClick()
    return this
  }

  fun assertAddedOrganization(amount: Int): OrganizationsScreenRobot {
    composeTestRule.waitForListItems(OrganizationsScreenTestTag.ORGANIZATION_CARD)
    composeTestRule.assertListItems(OrganizationsScreenTestTag.ORGANIZATION_CARD, amount = amount)
    return this
  }

  fun assertNoAddedOrganizations(): OrganizationsScreenRobot {
    composeTestRule.onNodeWithTag(OrganizationsScreenTestTag.EMPTY_STATE).isDisplayed()
    return this
  }

  fun clickFirstOrganization(): OrganizationsScreenRobot {
    composeTestRule.waitForListItems(OrganizationsScreenTestTag.ORGANIZATION_CARD)
    composeTestRule.onAllNodesWithTag(OrganizationsScreenTestTag.ORGANIZATION_CARD).onFirst().performClick()
    return this
  }

  fun isDisplayedOrganizationRemovedSnackbar(): OrganizationsScreenRobot {
    composeTestRule.onNodeWithTag(MgoSnackbarTestTag.SNACKBAR).isDisplayed()
    return this
  }

  fun gotoAddOrganizationScreen(): AddOrganizationScreenRobot = AddOrganizationScreenRobot(composeTestRule)

  fun gotoHealthCategoriesScreen(): HealthCategoriesScreenRobot = HealthCategoriesScreenRobot(composeTestRule)
}
