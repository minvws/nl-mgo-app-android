package nl.rijksoverheid.mgo.component.mgo

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MgoButtonSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun solid() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoButtonPrimarySolidPreview()
    }
  }

  @Test
  fun solidLoading() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoButtonPrimarySolidLoadingPreview()
    }
  }

  @Test
  fun solidIcon() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoButtonPrimarySolidWithIconPreview()
    }
  }

  @Test
  fun tonal() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoButtonPrimaryTonalPreview()
    }
  }

  @Test
  fun ghost() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoButtonPrimaryGhostPreview()
    }
  }
}
