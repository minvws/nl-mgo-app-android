package nl.rijksoverheid.mgo.component.theme.samples

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class ThemeSampleScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun backgrounds() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      BackgroundsPreview()
    }
  }

  @Test
  fun labels() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      LabelsPreview()
    }
  }

  @Test
  fun symbols() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      SymbolsPreview()
    }
  }

  @Test
  fun categories() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      CategoriesPreview()
    }
  }

  @Test
  fun actions() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      ActionsPreview()
    }
  }

  @Test
  fun states() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      StatesPreview()
    }
  }
}
