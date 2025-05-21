package nl.rijksoverheid.mgo.component.pincode.keyboard

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class KeyboardSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun preview() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      KeyboardPreview()
    }
  }

  @Test
  fun withBiometric() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      KeyboardWithBiometricPreview()
    }
  }
}
