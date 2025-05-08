package nl.rijksoverheid.mgo.component.mgo

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MgoButtonSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun primaryDefault() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoButtonPrimaryDefaultPreview()
    }
  }

  @Test
  fun primaryDefaultLoading() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoButtonPrimaryDefaultLoadingPreview()
    }
  }

  @Test
  fun primaryNegative() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoButtonPrimaryNegativePreview()
    }
  }

  @Test
  fun secondaryDefault() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoButtonSecondaryDefaultPreview()
    }
  }

  @Test
  fun secondaryNegative() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoButtonSecondaryNegativePreview()
    }
  }

  @Test
  fun tertiaryDefault() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoButtonTertiaryDefaultPreview()
    }
  }

  @Test
  fun tertiaryDefaultLoading() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoButtonTertiaryDefaultLoadingPreview()
    }
  }

  @Test
  fun tertiaryNegative() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoButtonTertiaryNegativePreview()
    }
  }

  @Test
  fun digid() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoButtonDigidPreview()
    }
  }

  @Test
  fun link() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      MgoButtonLinkPreview()
    }
  }
}
