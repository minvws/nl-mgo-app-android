package nl.rijksoverheid.mgo.component.theme

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeUp
import org.junit.Rule
import org.junit.Test

class ColumnWithButtonTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @Test
    fun elevationIsNotVisibleWhenContentIsNotScrollable() {
        composeTestRule.setContent {
            MgoTheme {
                ColumnWithButton(buttonText = "Button", onButtonClick = { }) {
                    PreviewTextNotScrolling()
                }
            }
        }
        composeTestRule.onNodeWithTag(TEST_TAG_COLUMN_WITH_BUTTON_ELEVATION).assertDoesNotExist()
    }

    @Test
    fun elevationIsVisibleWhenContentIsScrollable() {
        composeTestRule.setContent {
            MgoTheme {
                ColumnWithButton(buttonText = "Button", onButtonClick = { }) {
                    PreviewTextScrolling()
                }
            }
        }
        composeTestRule.onNodeWithTag(TEST_TAG_COLUMN_WITH_BUTTON_ELEVATION).assertExists()
    }

    @Test
    fun elevationIsNotVisibleWhenContentIsScrollableAndScrolledToBottom() {
        composeTestRule.setContent {
            MgoTheme {
                ColumnWithButton(buttonText = "Button", onButtonClick = { }) {
                    PreviewTextScrolling()
                }
            }
        }
        composeTestRule.onNodeWithTag(TEST_TAG_COLUMN_WITH_BUTTON_SCROLLABLE_COLUMN).performTouchInput {
            // This can probably be a lot nicer, but this gets the job done to scroll to the bottom of the column.
            for (i in 0 until 100) {
                swipeUp()
            }
        }
        composeTestRule.onNodeWithTag(TEST_TAG_COLUMN_WITH_BUTTON_ELEVATION).assertDoesNotExist()
    }
}
