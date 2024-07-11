package nl.rijksoverheid.mgo.feature.localisation.organizationSearch

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class OrganizationSearchScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun loading() {
        snapshotTestRule.snapshots {
            OrganizationSearchScreenLoadingPreview()
        }
    }

    @Test
    fun empty() {
        snapshotTestRule.snapshots {
            OrganizationSearchScreenEmptyPreview()
        }
    }

    @Test
    fun searchResults() {
        snapshotTestRule.snapshots {
            OrganizationSearchScreenSearchResultsPreview()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots {
            OrganizationSearchScreenErrorPreview()
        }
    }
}
