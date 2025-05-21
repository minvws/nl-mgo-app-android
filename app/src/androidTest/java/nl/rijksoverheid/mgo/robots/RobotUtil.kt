package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag
import androidx.compose.ui.test.onFirst
import androidx.compose.ui.test.performClick

fun waitForListItems(
  composeTestRule: ComposeTestRule,
  listItemTestTag: String,
  block: () -> Unit,
) {
  composeTestRule.waitUntil(timeoutMillis = 5000L) {
    composeTestRule
      .onAllNodesWithTag(listItemTestTag)
      .fetchSemanticsNodes().size > 1
  }
  block()
}

fun clickFirstListItem(
  composeTestRule: ComposeTestRule,
  listItemTestTag: String,
  block: () -> Unit,
) {
  composeTestRule.onAllNodesWithTag(listItemTestTag).onFirst().performClick()
  block()
}

fun assertOneListItem(
  composeTestRule: ComposeTestRule,
  testTag: String,
) {
  composeTestRule
    .onAllNodesWithTag(testTag)
    .onFirst()
    .assertExists()
}

fun assertNoListItems(
  composeTestRule: ComposeTestRule,
  testTag: String,
): Boolean {
  return composeTestRule
    .onAllNodesWithTag(testTag)
    .fetchSemanticsNodes().isEmpty()
}
