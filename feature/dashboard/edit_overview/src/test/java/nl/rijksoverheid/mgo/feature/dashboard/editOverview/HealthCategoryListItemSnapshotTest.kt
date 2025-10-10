package nl.rijksoverheid.mgo.feature.dashboard.editOverview

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class HealthCategoryListItemSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun preview() {
    snapshotTestRule.snapshots {
      HealthCategoryListItemPreview()
    }
  }
}
