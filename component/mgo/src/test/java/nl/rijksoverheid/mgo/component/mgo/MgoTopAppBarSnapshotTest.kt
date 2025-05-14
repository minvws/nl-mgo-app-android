package nl.rijksoverheid.mgo.component.mgo

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MgoTopAppBarSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun withoutBackButton() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoTopAppBarPreview()
    }
  }

  @Test
  fun withBackButton() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoTopAppBarWithBackButtonPreview()
    }
  }
}
