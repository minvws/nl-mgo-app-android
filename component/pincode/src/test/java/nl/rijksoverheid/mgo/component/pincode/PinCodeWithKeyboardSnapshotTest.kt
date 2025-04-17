package nl.rijksoverheid.mgo.component.pincode

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class PinCodeWithKeyboardSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun preview() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      PinCodeWithKeyboardPreview()
    }
  }

  @Test
  fun hint() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      PinCodeWithKeyboardAndHintPreview()
    }
  }
}
