package nl.rijksoverheid.mgo.feature.healthcareprovider.removeOrganization

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class RemoveOrganizationScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun preview() {
        snapshotTestRule.snapshots {
            RemoveOrganizationScreenPreview()
        }
    }
}
