package nl.rijksoverheid.mgo.feature.digid

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class DigidLoginScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun fromOnboarding() {
    snapshotTestRule.snapshots {
      DigidLoginScreenFromOnboardingPreview()
    }
  }

  @Test
  fun default() {
    snapshotTestRule.snapshots {
      DigidLoginScreenPreview()
    }
  }
}
