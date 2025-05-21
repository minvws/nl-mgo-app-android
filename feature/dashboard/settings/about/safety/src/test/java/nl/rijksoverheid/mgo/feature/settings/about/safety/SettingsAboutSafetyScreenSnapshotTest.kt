package nl.rijksoverheid.mgo.feature.settings.about.safety

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class SettingsAboutSafetyScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun launchView() {
    snapshotTestRule.snapshots {
      SettingsAboutSafetyScreenPreview()
    }
  }
}
