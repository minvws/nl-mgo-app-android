package nl.rijksoverheid.mgo.feature.settings.about.home.versions

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class SettingsHomeVersionsScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun launchView() {
    snapshotTestRule.snapshots {
      SettingsHomeVersionsScreenPreview()
    }
  }
}
