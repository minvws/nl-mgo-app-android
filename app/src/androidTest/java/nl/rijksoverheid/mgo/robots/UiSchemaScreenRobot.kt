package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performScrollToNode
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.UiSchemaBottomSheetTestTag
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.UiSchemaScreenTestTag

class UiSchemaScreenRobot(
  private val composeTestRule: ComposeTestRule,
) {
  fun clickRow(name: String): UiSchemaScreenRobot {
    composeTestRule
      .onNodeWithTag(
        UiSchemaScreenTestTag.LIST,
      ).performScrollToNode(hasText(name))
    composeTestRule.onNodeWithText(name).performClick()
    return this
  }

  fun isDisplayedBottomSheet(): UiSchemaScreenRobot {
    composeTestRule.onNodeWithTag(UiSchemaBottomSheetTestTag.SHEET).isDisplayed()
    return this
  }
}
