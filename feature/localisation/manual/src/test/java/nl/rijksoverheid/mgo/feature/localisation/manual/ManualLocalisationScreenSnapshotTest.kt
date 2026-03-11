package nl.rijksoverheid.mgo.feature.localisation.manual

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class ManualLocalisationScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun testEmpty() {
    snapshotTestRule.snapshots {
      ManualLocalisationScreenEmptyPreview()
    }
  }

  @Test
  fun testNotEmpty() {
    snapshotTestRule.snapshots {
      ManualLocalisationScreenPreview()
    }
  }
}
