package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class HealthCategoriesListItemSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun loading() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      HealthCategoriesListItemLoadedPreview()
    }
  }

  @Test
  fun noData() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      HealthCategoriesListItemNoDataPreview()
    }
  }

  @Test
  fun loaded() {
    snapshotTestRule.snapshots {
      HealthCategoriesListItemLoadedPreview()
    }
  }
}
