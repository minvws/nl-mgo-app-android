package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class HealthCategoriesFavoriteCardSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun notLoading() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      HealthCategoriesFavoriteCardPreview()
    }
  }

  @Test
  fun loading() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      HealthCategoriesFavoriteCardLoadingPreview()
    }
  }

  @Test
  fun multiLine() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      HealthCategoriesFavoriteMultilineCardPreview()
    }
  }
}
