package nl.rijksoverheid.mgo.feature.localisation.organizationSearch.automatic

import nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic.OrganizationListAutomaticSearchScreenErrorPreview
import nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic.OrganizationListAutomaticSearchScreenLoadingPreview
import nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic.OrganizationListAutomaticSearchScreenSearchResultsPreview
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class OrganizationListAutomaticScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun loading() {
        snapshotTestRule.snapshots {
            OrganizationListAutomaticSearchScreenLoadingPreview()
        }
    }

    @Test
    fun searchResults() {
        snapshotTestRule.snapshots {
            OrganizationListAutomaticSearchScreenSearchResultsPreview()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots {
            OrganizationListAutomaticSearchScreenErrorPreview()
        }
    }
}
