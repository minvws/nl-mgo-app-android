package nl.rijksoverheid.mgo.feature.settings.about.home

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class SettingsAboutHomeScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun launchView() {
    snapshotTestRule.snapshots {
      SettingsAboutHomeScreenPreview()
    }
  }
}
