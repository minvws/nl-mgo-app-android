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
            SearchResultsLoadingPreview()
        }
    }

    @Test
    fun searchResults() {
        snapshotTestRule.snapshots {
            SearchResultsPreview()
        }
    }

    @Test
    fun empty() {
        snapshotTestRule.snapshots {
            SearchResultsEmptyPreview()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots {
            SearchResultsErrorPreview()
        }
    }
}
