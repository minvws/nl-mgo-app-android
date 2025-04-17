package nl.rijksoverheid.mgo.feature.settings.home

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class SettingsHomeScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun preview() {
    snapshotTestRule.snapshots {
      SettingsHomeScreenPreview()
    }
  }

  @Test
  fun withoutBiometric() {
    snapshotTestRule.snapshots {
      SettingsHomeScreenWithoutBiometricPreview()
    }
  }

  @Test
  fun withoutDebug() {
    snapshotTestRule.snapshots {
      SettingsHomeScreenWithoutDebugPreview()
    }
  }
}
