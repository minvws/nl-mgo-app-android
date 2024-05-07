package nl.rijksoverheid.mgo.feature.localisation.searchresults

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class SearchResultsScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun loading() {
        snapshotTestRule.snapshots {
            HealthCareSearchResultsLoadingPreview()
        }
    }

    @Test
    fun empty() {
        snapshotTestRule.snapshots {
            HealthCareSearchResultsEmptyPreview()
        }
    }

    @Test
    fun searchResults() {
        snapshotTestRule.snapshots {
            HealthCareSearchResultsPreview()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots {
            HealthCareSearchResultsErrorPreview()
        }
    }
}
