package nl.rijksoverheid.mgo

import nl.rijksoverheid.mgo.feature.onboarding.IntroductionScreenPreview
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
