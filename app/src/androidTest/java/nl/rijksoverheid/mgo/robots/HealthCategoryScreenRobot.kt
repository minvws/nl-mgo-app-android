package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick
import nl.rijksoverheid.mgo.feature.dashboard.healthCategory.HealthCategoryScreenTestTag
import nl.rijksoverheid.mgo.utils.assertListItems
import nl.rijksoverheid.mgo.utils.waitForListItems

class HealthCategoryScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun assertCardsExists(): HealthCategoryScreenRobot {
    composeTestRule.waitForListItems(HealthCategoryScreenTestTag.CARD)
    composeTestRule.assertListItems(HealthCategoryScreenTestTag.CARD)
    return this
  }

  fun clickFirstListItem(): HealthCategoryScreenRobot {
    composeTestRule.waitForListItems(HealthCategoryScreenTestTag.CARD)
    composeTestRule.onAllNodesWithTag(HealthCategoryScreenTestTag.CARD).onFirst().performClick()
    return this
  }

  fun gotoUiSchemaScreen(): UiSchemaScreenRobot = UiSchemaScreenRobot(composeTestRule)
}
