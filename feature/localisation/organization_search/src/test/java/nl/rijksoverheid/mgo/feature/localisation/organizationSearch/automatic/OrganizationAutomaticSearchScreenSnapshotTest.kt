package nl.rijksoverheid.mgo.feature.localisation.organizationSearch.automatic

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class OrganizationAutomaticSearchScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun loading() {
        snapshotTestRule.snapshots {
            OrganizationAutomaticSearchScreenLoadingPreview()
        }
    }

    @Test
    fun searchResults() {
        snapshotTestRule.snapshots {
            OrganizationAutomaticSearchScreenSearchResultsPreview()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots {
            OrganizationAutomaticSearchScreenErrorPreview()
        }
    }
}
