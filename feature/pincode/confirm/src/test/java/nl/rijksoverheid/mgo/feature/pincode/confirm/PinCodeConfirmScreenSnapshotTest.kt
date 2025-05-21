package nl.rijksoverheid.mgo.feature.pincode.confirm

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class PinCodeConfirmScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun preview() {
    snapshotTestRule.snapshots {
      PinCodeConfirmScreenPreview()
    }
  }

  @Test
  fun error() {
    snapshotTestRule.snapshots {
      PinCodeConfirmScreenErrorPreview()
    }
  }
}
