package nl.rijksoverheid.mgo.feature.dashboard.editOverview

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class EditOverviewBottomSheetSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun noFavorites() {
    snapshotTestRule.snapshots {
      EditOverviewBottomSheetNoFavoritesPreview()
    }
  }

  @Test
  fun favorites() {
    snapshotTestRule.snapshots {
      EditOverviewBottomSheetFavoritesPreview()
    }
  }
}
