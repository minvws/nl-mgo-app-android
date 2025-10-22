package nl.rijksoverheid.mgo.component.theme.samples

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class ColorSampleScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun basicColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      BasicColorsPreview()
    }
  }

  @Test
  fun grayColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      GrayColorsPreview()
    }
  }

  @Test
  fun coolGrayColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      CoolGrayColorsPreview()
    }
  }

  @Test
  fun logoBlueColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      LogoBlueColorsPreview()
    }
  }

  @Test
  fun skyBlueColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      SkyBlueColorsPreview()
    }
  }

  @Test
  fun darkBlueColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      DarkBlueColorsPreview()
    }
  }

  @Test
  fun lightBlueColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      LightBlueColorsPreview()
    }
  }

  @Test
  fun greenColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      GreenColorsPreview()
    }
  }

  @Test
  fun darkGreenColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      DarkGreenColorsPreview()
    }
  }

  @Test
  fun mintColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      MintColorsPreview()
    }
  }

  @Test
  fun mossColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      MossColorsPreview()
    }
  }

  @Test
  fun yellowColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      YellowColorsPreview()
    }
  }

  @Test
  fun darkYellowColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      DarkYellowColorsPreview()
    }
  }

  @Test
  fun orangeColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      OrangeColorsPreview()
    }
  }

  @Test
  fun redColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      RedColorsPreview()
    }
  }

  @Test
  fun rubyColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      RubyColorsPreview()
    }
  }

  @Test
  fun violetColors() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      VioletColorsPreview()
    }
  }

  @Test
  fun pinkColorsPreview() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      PinkColorsPreview()
    }
  }

  @Test
  fun purpleColorsPreview() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      PurpleColorsPreview()
    }
  }

  @Test
  fun brownColorsPreview() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      BrownColorsPreview()
    }
  }

  @Test
  fun darkBrownColorsPreview() {
    snapshotTestRule.snapshots(devices = SnapshotDevices.PhoneLightDarkPortrait) {
      DarkBrownColorsPreview()
    }
  }
}
