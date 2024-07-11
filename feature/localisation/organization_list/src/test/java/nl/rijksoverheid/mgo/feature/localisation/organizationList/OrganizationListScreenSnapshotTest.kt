package nl.rijksoverheid.mgo.feature.localisation.organizationList

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class OrganizationListScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun preview() {
        snapshotTestRule.snapshots {
            OrganizationListScreenPreview()
        }
    }
}
