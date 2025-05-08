package nl.rijksoverheid.mgo.component.pincode.pincode

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class PinCodeSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun empty() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      PinCodeEmptyPreview()
    }
  }

  @Test
  fun halfFilled() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      PinCodeHalfFilledPreview()
    }
  }
}
