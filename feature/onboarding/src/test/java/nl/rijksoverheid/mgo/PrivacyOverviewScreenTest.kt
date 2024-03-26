package nl.rijksoverheid.mgo

import nl.rijksoverheid.mgo.feature.onboarding.PrivacyOverviewScreenPreview
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class PrivacyOverviewScreenTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun launchView() {
        snapshotTestRule.snapshots {
            PrivacyOverviewScreenPreview()
        }
    }
}
