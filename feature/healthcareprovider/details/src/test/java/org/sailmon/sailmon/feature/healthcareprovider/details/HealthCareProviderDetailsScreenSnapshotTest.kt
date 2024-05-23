package org.sailmon.sailmon.feature.healthcareprovider.details

import nl.rijksoverheid.mgo.feature.healthcareprovider.details.HealthCareProviderDetailsScreenPreview
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class StoredHealthCareProvidersScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun preview() {
        snapshotTestRule.snapshots {
            HealthCareProviderDetailsScreenPreview()
        }
    }
}
