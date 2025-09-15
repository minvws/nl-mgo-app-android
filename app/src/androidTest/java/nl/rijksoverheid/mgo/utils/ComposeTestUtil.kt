package nl.rijksoverheid.mgo.utils

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onAllNodesWithTag

fun ComposeTestRule.waitForListItems(listItemTestTag: String) {
  waitUntil(timeoutMillis = 20000L) {
    onAllNodesWithTag(listItemTestTag)
      .fetchSemanticsNodes()
      .isNotEmpty()
  }
}

fun ComposeTestRule.assertListItems(listItemTestTag: String) {
  val nodes =
    onAllNodesWithTag(listItemTestTag)
      .fetchSemanticsNodes()

  assert(nodes.isNotEmpty()) {
    "Expected more than 1 item with tag '$listItemTestTag', but found ${nodes.size}."
  }
}

fun ComposeTestRule.assertListItems(
  listItemTestTag: String,
  amount: Int,
) {
  val nodes =
    onAllNodesWithTag(listItemTestTag)
      .fetchSemanticsNodes()

  assert(nodes.size == amount) {
    "Expected $amount items with tag '$listItemTestTag', but found ${nodes.size}."
  }
}
