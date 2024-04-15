package nl.rijksoverheid.mgo.feature.localisation

import nl.rijksoverheid.mgo.feature.localisation.search.SearchScreenPreview
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class SearchScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun launchView() {
        snapshotTestRule.snapshots {
            SearchScreenPreview()
        }
    }
}
