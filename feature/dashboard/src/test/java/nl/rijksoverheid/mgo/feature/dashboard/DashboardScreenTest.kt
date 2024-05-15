package nl.rijksoverheid.mgo.feature.dashboard

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class DashboardScreenTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun bottomNavigationBar() {
        snapshotTestRule.snapshots {
            DashboardBottomNavigationBar()
        }
    }
}
