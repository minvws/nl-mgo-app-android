package nl.rijksoverheid.mgo.feature.digid

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class DigidLoginScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun idle() {
    snapshotTestRule.snapshots {
      DigidLoginScreenIdlePreview()
    }
  }

  @Test
  fun loading() {
    snapshotTestRule.snapshots {
      DigidLoginScreenLoadingPreview()
    }
  }
}
