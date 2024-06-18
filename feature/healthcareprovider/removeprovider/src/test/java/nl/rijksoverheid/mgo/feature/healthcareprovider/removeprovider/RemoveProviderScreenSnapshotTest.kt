package nl.rijksoverheid.mgo.feature.healthcareprovider.removeprovider

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class RemoveProviderScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun preview() {
        snapshotTestRule.snapshots {
            RemoveProviderScreenPreview()
        }
    }
}
