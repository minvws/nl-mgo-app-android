package nl.rijksoverheid.mgo.feature.settings.about.opensource

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class SettingsAboutOpenSourceScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun launchView() {
    snapshotTestRule.snapshots {
      SettingsAboutOpenSourceScreenPreview()
    }
  }
}
