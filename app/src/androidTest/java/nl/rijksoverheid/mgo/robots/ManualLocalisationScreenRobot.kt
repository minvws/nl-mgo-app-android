package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import androidx.compose.ui.test.performTextInput
import nl.rijksoverheid.mgo.component.mgo.MgoAlertDialogTestTag
import nl.rijksoverheid.mgo.feature.localisation.manual.ManualLocalisationScreenTestTag
import nl.rijksoverheid.mgo.utils.waitForListItems

class ManualLocalisationScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun setSearchInput(input: String): ManualLocalisationScreenRobot {
    composeTestRule
      .onNodeWithTag(ManualLocalisationScreenTestTag.SEARCH_INPUT)
      .performTextInput(input)
    return this
  }

  fun addOrganization(name: String): ManualLocalisationScreenRobot {
    composeTestRule.waitForListItems(ManualLocalisationScreenTestTag.CARD)
    composeTestRule.onNodeWithTag(ManualLocalisationScreenTestTag.LIST).performScrollToNode(hasText(name))
    composeTestRule.onNodeWithText(name).performClick()
    composeTestRule.onNodeWithTag(MgoAlertDialogTestTag.CONFIRM_BUTTON).performClick()
    return this
  }

  fun gotoHealthCategoriesScreen(): HealthCategoriesScreenRobot = HealthCategoriesScreenRobot(composeTestRule)

  fun gotoOrganizationsScreen(): OrganizationsScreenRobot = OrganizationsScreenRobot(composeTestRule)
}
