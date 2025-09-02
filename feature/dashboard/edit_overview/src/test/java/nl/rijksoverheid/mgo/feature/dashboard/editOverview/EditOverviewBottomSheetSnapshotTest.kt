package nl.rijksoverheid.mgo.feature.dashboard.editOverview

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

class EditOverviewBottomSheetSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun noFavoritesPreview() {
    snapshotTestRule.snapshots {
      EditOverviewBottomSheetNoFavoritesPreview()
    }
  }

  @Test
  fun favoritesPreview() {
    snapshotTestRule.snapshots {
      EditOverviewBottomSheetFavoritesPreview()
    }
  }
}
