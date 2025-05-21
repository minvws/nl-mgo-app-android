package nl.rijksoverheid.mgo.feature.pincode.create

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class PinCodeCreateScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun preview() {
    snapshotTestRule.snapshots {
      PinCodeCreateScreenPreview()
    }
  }

  @Test
  fun error() {
    snapshotTestRule.snapshots {
      PinCodeCreateScreenErrorPreview()
    }
  }
}
