package nl.rijksoverheid.mgo.feature.dashboard.healthCategories.listItem

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class HealthCategoriesFavoriteCardSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun preview() {
    snapshotTestRule.snapshots {
      HealthCategoriesFavoriteCardPreview()
    }
  }

  @Test
  fun multiline() {
    snapshotTestRule.snapshots {
      HealthCategoriesFavoriteMultilineCardPreview()
    }
  }

  @Test
  fun loading() {
    snapshotTestRule.snapshots {
      HealthCategoriesFavoriteCardLoadingPreview()
    }
  }
}
