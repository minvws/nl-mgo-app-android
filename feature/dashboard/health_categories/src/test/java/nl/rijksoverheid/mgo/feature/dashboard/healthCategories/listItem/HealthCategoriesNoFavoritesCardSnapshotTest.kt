package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class HealthCategoriesNoFavoritesCardSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun preview() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      HealthCategoriesNoFavoriteCardPreview()
    }
  }
}
