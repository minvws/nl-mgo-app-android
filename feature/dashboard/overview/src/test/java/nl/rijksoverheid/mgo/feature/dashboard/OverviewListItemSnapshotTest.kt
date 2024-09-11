package nl.rijksoverheid.mgo.feature.dashboard

import nl.rijksoverheid.mgo.feature.dashboard.overview.listItem.OverviewListItemLoadedPreview
import nl.rijksoverheid.mgo.feature.dashboard.overview.listItem.OverviewListItemNoDataPreview
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class OverviewListItemSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun loading() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            OverviewListItemLoadedPreview()
        }
    }

    @Test
    fun noData() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            OverviewListItemNoDataPreview()
        }
    }

    @Test
    fun loaded() {
        snapshotTestRule.snapshots {
            OverviewListItemLoadedPreview()
        }
    }
}
