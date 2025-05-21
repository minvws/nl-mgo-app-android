package nl.rijksoverheid.mgo.feature.settings.about.accessibility

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class SettingsAboutAccessibilityScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun launchView() {
    snapshotTestRule.snapshots {
      SettingsAboutAccessibilityScreenPreview()
    }
  }
}
