package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.junit4.ComposeTestRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import nl.rijksoverheid.mgo.component.theme.TEST_TAG_COLUMN_WITH_BUTTON_PRIMARY_BUTTON
import nl.rijksoverheid.mgo.feature.dashboard.overview.TEST_TAG_HEALTH_PROVIDER_CARD

internal class DashboardScreenRobot(private val composeTestRule: ComposeTestRule) {
    internal fun assertNoProviders() {
        assertNoListItems(composeTestRule = composeTestRule, testTag = TEST_TAG_HEALTH_PROVIDER_CARD)
    }

    internal fun assertOneProvider() {
        assertOneListItem(composeTestRule = composeTestRule, testTag = TEST_TAG_HEALTH_PROVIDER_CARD)
    }

    internal fun clickLocalisationButton(block: SearchScreenRobot.() -> Unit) {
        composeTestRule.onNodeWithTag(TEST_TAG_COLUMN_WITH_BUTTON_PRIMARY_BUTTON).performClick()
        block(SearchScreenRobot(composeTestRule))
    }
}
