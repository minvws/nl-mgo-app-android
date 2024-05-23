package nl.rijksoverheid.mgo.feature.localisation.searchresults

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class SearchResultCardSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun added() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            SearchResultCardAddedPreview()
        }
    }

    @Test
    fun notAdded() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            SearchResultCardNotAddedPreview()
        }
    }
}
