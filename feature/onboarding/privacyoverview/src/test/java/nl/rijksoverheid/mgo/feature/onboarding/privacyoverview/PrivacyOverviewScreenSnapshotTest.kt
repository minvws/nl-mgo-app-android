package nl.rijksoverheid.mgo.feature.onboarding.privacyoverview

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class PrivacyOverviewScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun launchView() {
        snapshotTestRule.snapshots {
            PrivacyOverviewScreenPreview()
        }
    }
}
