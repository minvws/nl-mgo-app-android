package nl.rijksoverheid.mgo.feature.localisation.stored

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class StoredHealthCareProvidersScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun preview() {
        snapshotTestRule.snapshots {
            StoredHealthCareProvidersScreenPreview()
        }
    }

    fun dialog() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            RemoveProviderDialogPreview()
        }
    }
}
