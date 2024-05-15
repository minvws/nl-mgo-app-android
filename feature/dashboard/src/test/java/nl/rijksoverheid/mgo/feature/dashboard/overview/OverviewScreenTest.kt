package nl.rijksoverheid.mgo.feature.dashboard.overview

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class OverviewScreenTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun withProviders() {
        snapshotTestRule.snapshots {
            OverviewScreenWithProvidersPreview()
        }
    }

    @Test
    fun emptyState() {
        snapshotTestRule.snapshots {
            OverviewScreenEmptyStatePreview()
        }
    }
}
