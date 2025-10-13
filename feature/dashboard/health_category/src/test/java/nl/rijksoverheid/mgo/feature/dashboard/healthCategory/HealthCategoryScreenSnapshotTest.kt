package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class HealthCategoryScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun loading() {
    snapshotTestRule.snapshots {
      HealthCategoryScreenLoadingPreview()
    }
  }

  @Test
  fun loaded() {
    snapshotTestRule.snapshots {
      HealthCategoryScreenLoadedPreview()
    }
  }

  @Test
  fun error() {
    snapshotTestRule.snapshots {
      HealthCategoryScreenListItemsWithErrorPreview()
    }
  }

  @Test
  fun noData() {
    snapshotTestRule.snapshots {
      HealthCategoryScreenNoDataPreview()
    }
  }
}
