package nl.rijksoverheid.mgo.component.mgo.banner

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MgoBannerSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun infoBanner() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      InfoBannerPreview()
    }
  }

  @Test
  fun infoBannerWithButton() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      InfoBannerPreview()
    }
  }

  @Test
  fun successBanner() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      SuccessBannerPreview()
    }
  }

  @Test
  fun warningBanner() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      WarningBannerPreview()
    }
  }

  @Test
  fun errorBanner() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      ErrorBannerPreview()
    }
  }
}
