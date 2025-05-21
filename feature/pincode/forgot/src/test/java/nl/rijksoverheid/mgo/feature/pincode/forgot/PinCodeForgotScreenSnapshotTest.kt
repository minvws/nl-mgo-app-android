package nl.rijksoverheid.mgo.feature.pincode.forgot

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class PinCodeForgotScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun preview() {
    snapshotTestRule.snapshots {
      PinCodeForgotScreenPreview()
    }
  }
}
