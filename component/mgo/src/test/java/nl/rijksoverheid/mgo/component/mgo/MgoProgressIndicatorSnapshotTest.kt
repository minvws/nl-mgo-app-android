package nl.rijksoverheid.mgo.component.mgo

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MgoProgressIndicatorSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun small() {
    snapshotTestRule.snapshots {
      MgoProgressIndicatorSmallPreview()
    }
  }

  @Test
  fun large() {
    snapshotTestRule.snapshots {
      MgoProgressIndicatorLargePreview()
    }
  }
}
