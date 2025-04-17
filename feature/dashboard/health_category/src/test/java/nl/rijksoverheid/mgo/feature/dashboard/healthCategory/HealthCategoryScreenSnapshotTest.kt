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
  fun listItems() {
    snapshotTestRule.snapshots {
      HealthCategoryScreenListItemsPreview()
    }
  }

  @Test
  fun listItemsWithError() {
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

  @Test
  fun noDataWithError() {
    snapshotTestRule.snapshots {
      HealthCategoryScreenNoDataWithErrorPreview()
    }
  }
}
