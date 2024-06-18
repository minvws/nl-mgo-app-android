package nl.rijksoverheid.mgo.feature.healthcareprovider.details

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class HealthCareProviderDetailsScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun preview() {
        snapshotTestRule.snapshots {
            HealthCareProviderDetailsScreenPreview()
        }
    }
}
