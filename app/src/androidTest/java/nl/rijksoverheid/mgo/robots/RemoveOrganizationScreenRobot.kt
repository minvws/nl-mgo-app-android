package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtonsTestTag

class RemoveOrganizationScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun clickRemoveButton(): RemoveOrganizationScreenRobot {
    composeTestRule.onNodeWithTag(MgoBottomButtonsTestTag.SECONDARY_BUTTON).performClick()
    return this
  }

  fun gotoOrganizationScreen(): OrganizationsScreenRobot = OrganizationsScreenRobot(composeTestRule)
}
