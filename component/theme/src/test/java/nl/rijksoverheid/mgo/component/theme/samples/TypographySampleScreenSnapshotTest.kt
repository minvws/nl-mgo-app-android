package nl.rijksoverheid.mgo.component.theme.samples

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class TypographySampleScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun launchView() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      TypographySampleScreenPreview()
    }
  }
}
