package nl.rijksoverheid.mgo.feature.onboarding.introduction

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class IntroductionScreenTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun launchView() {
    snapshotTestRule.snapshots {
      IntroductionScreenPreview()
    }
  }
}
