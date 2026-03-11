package nl.rijksoverheid.mgo.feature.localisation.manual

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class ManualLocalisationCardSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun testDisabled() {
    snapshotTestRule.snapshots {
      ManualLocalisationCardDisabledPreview()
    }
  }

  @Test
  fun testEnabled() {
    snapshotTestRule.snapshots {
      ManualLocalisationCardPreview()
    }
  }
}
