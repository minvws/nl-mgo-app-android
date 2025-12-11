package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.hasTestTag
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtonsTestTag
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.HealthCategoriesScreenTestTag

class HealthCategoriesScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun clickAddOrganizationButton(): HealthCategoriesScreenRobot {
    composeTestRule
      .onNodeWithTag(MgoBottomButtonsTestTag.PRIMARY_BUTTON)
      .performClick()
    return this
  }

  fun clickCategory(name: String): HealthCategoriesScreenRobot {
    composeTestRule.onNodeWithTag(HealthCategoriesScreenTestTag.LIST).performScrollToNode(hasText(name))
    composeTestRule
      .onNodeWithText(name)
      .performClick()
    return this
  }

  fun clickDeleteOrganizationButton(): HealthCategoriesScreenRobot {
    composeTestRule
      .onNodeWithTag(
        HealthCategoriesScreenTestTag.LIST,
      ).performScrollToNode(hasTestTag(HealthCategoriesScreenTestTag.DELETE_ORGANIZATION_BUTTON))
    composeTestRule.onNodeWithTag(HealthCategoriesScreenTestTag.DELETE_ORGANIZATION_BUTTON).performClick()
    return this
  }

  fun gotoAddOrganizationScreen(): AddOrganizationScreenRobot = AddOrganizationScreenRobot(composeTestRule)

  fun gotoHealthCategoryScreen(): HealthCategoryScreenRobot = HealthCategoryScreenRobot(composeTestRule)

  fun gotoRemoveOrganizationsScreen(): RemoveOrganizationScreenRobot = RemoveOrganizationScreenRobot(composeTestRule)
}
