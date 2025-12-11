package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class PftBottomSheetSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun preview() {
    snapshotTestRule.snapshots {
      PftBottomSheetPreview()
    }
  }
}
