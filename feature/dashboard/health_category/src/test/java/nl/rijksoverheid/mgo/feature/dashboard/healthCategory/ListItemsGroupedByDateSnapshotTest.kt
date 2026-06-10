package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class ListItemsGroupedByDateSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun preview() {
    snapshotTestRule.snapshots {
      ListItemsGroupedByDatePreview()
    }
  }
}
