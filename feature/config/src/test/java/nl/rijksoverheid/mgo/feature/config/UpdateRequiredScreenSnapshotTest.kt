package nl.rijksoverheid.mgo.feature.config

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class UpdateRequiredScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun launchView() {
        snapshotTestRule.snapshots {
            UpdateRequiredScreenPreview()
        }
    }
}
