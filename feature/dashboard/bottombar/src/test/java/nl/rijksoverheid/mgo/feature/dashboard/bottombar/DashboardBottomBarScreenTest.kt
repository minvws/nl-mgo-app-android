package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class DashboardBottomBarScreenTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun bottomNavigationBar() {
    snapshotTestRule.snapshots {
      DashboardBottomBarScreenPreview()
    }
  }
}
