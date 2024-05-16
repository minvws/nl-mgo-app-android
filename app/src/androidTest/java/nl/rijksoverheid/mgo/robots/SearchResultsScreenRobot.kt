package nl.rijksoverheid.mgo.robots

import androidx.compose.ui.test.junit4.ComposeTestRule
import nl.rijksoverheid.mgo.feature.localisation.searchresults.TEST_TAG_SEARCH_RESULT_CARD

internal class SearchResultsScreenRobot(private val composeTestRule: ComposeTestRule) {
    fun clickFirstSearchResult(block: StoredHealthCareProvidersScreenRobot.() -> Unit) {
        waitForListItems(composeTestRule = composeTestRule, listItemTestTag = TEST_TAG_SEARCH_RESULT_CARD) {
            clickFirstListItem(composeTestRule = composeTestRule, listItemTestTag = TEST_TAG_SEARCH_RESULT_CARD) {
                block(StoredHealthCareProvidersScreenRobot(composeTestRule))
            }
        }
    }
}
