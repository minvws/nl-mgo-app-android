package nl.rijksoverheid.mgo.feature.onboarding.proposition

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class PropositionScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun launchView() {
    snapshotTestRule.snapshots {
      PropositionScreenPreview()
    }
  }
}
