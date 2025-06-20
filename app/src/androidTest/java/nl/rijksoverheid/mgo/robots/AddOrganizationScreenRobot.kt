package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import nl.rijksoverheid.mgo.component.mgo.MgoBottomButtonsTestTag
import nl.rijksoverheid.mgo.feature.localisation.addOrganization.AddOrganizationScreenTestTag

class AddOrganizationScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun setNameTextInput(input: String): AddOrganizationScreenRobot {
    composeTestRule
      .onNodeWithTag(AddOrganizationScreenTestTag.NAME_TEXTFIELD)
      .performTextInput(input)
    return this
  }

  fun setCityTextInput(input: String): AddOrganizationScreenRobot {
    composeTestRule
      .onNodeWithTag(AddOrganizationScreenTestTag.CITY_TEXTFIELD)
      .performTextInput(input)
    return this
  }

  fun clickSearchButton(): AddOrganizationScreenRobot {
    composeTestRule
      .onNodeWithTag(MgoBottomButtonsTestTag.PRIMARY_BUTTON)
      .performClick()
    return this
  }

  fun gotoOrganizationListScreen(): OrganizationListScreenRobot = OrganizationListScreenRobot(composeTestRule)
}
