package nl.rijksoverheid.mgo.component.pincode.pincode

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class PinCodeItemSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun notFilled() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      PinCodeItemNotFilledPreview()
    }
  }

  @Test
  fun filled() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      PinCodeItemFilledPreview()
    }
  }
}
