package nl.rijksoverheid.mgo.feature.settings.display

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class SettingsDisplayScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun launchView() {
    snapshotTestRule.snapshots {
      SettingsDisplayScreenPreview()
    }
  }
}
