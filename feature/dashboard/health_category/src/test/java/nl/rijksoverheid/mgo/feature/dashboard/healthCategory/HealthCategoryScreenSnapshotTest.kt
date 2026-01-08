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
  fun noData() {
    snapshotTestRule.snapshots {
      HealthCategoryScreenNoDataPreview()
    }
  }

  @Test
  fun userError() {
    snapshotTestRule.snapshots {
      HealthCategoryScreenUserErrorPreview()
    }
  }

  @Test
  fun serverError() {
    snapshotTestRule.snapshots {
      HealthCategoryScreenServerErrorPreview()
    }
  }
}
