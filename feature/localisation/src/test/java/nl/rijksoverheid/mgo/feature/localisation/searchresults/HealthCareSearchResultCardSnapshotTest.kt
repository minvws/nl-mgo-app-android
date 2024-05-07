package nl.rijksoverheid.mgo.feature.localisation.searchresults

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class HealthCareSearchResultCardSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun added() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            HealthCareSearchResultCardAddedPreview()
        }
    }

    @Test
    fun notAdded() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            HealthCareSearchResultCardNotAddedPreview()
        }
    }
}
