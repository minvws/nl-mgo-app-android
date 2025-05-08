package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import nl.rijksoverheid.mgo.component.theme.TEST_TAG_COLUMN_WITH_BUTTON_PRIMARY_BUTTON
import nl.rijksoverheid.mgo.feature.localisation.addOrganization.TEST_TAG_CITY_TEXT_FIELD
import nl.rijksoverheid.mgo.feature.localisation.addOrganization.TEST_TAG_NAME_TEXT_FIELD

internal class AddOrganizationScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  internal fun inputName(name: String) {
    composeTestRule.onNodeWithTag(TEST_TAG_NAME_TEXT_FIELD).performTextInput(name)
  }

  internal fun inputCity(city: String) {
    composeTestRule.onNodeWithTag(TEST_TAG_CITY_TEXT_FIELD).performTextInput(city)
  }

  internal fun pressSearchButton(block: OrganizationSearchScreenRobot.() -> Unit) {
    composeTestRule.onNodeWithTag(TEST_TAG_COLUMN_WITH_BUTTON_PRIMARY_BUTTON).performClick()
    block(OrganizationSearchScreenRobot(composeTestRule))
  }
}
