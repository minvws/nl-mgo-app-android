package nl.rijksoverheid.mgo.feature.pincode.login

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class PinCodeLoginScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun preview() {
    snapshotTestRule.snapshots {
      PinCodeLoginScreenPreview()
    }
  }

  @Test
  fun withoutBiometric() {
    snapshotTestRule.snapshots {
      PinCodeLoginWithoutBiometricScreenPreview()
    }
  }

  @Test
  fun error() {
    snapshotTestRule.snapshots {
      PinCodeLoginScreenErrorPreview()
    }
  }
}
