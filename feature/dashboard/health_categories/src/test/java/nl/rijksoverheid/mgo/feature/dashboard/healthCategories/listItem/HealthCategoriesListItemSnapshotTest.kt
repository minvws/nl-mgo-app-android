package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class HealthCategoriesListItemSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun loading() {
    snapshotTestRule.snapshots {
      HealthCategoriesListItemLoadingPreview()
    }
  }

  @Test
  fun noData() {
    snapshotTestRule.snapshots {
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
