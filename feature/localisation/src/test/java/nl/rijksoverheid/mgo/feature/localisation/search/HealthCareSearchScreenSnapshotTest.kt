package nl.rijksoverheid.mgo.feature.localisation.search

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class HealthCareSearchScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun launchView() {
        snapshotTestRule.snapshots {
            HealthCareSearchScreenPreview()
        }
    }
}
