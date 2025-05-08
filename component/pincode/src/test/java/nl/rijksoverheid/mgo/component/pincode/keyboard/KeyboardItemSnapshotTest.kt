package nl.rijksoverheid.mgo.component.pincode.keyboard

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class KeyboardItemSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun icon() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      KeyboardItemIconPreview()
    }
  }

  @Test
  fun number() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      KeyboardItemNumberPreview()
    }
  }
}
