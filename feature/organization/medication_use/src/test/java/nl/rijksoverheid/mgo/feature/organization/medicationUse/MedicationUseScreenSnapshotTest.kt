package nl.rijksoverheid.mgo.feature.organization.medicationUse

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MedicationUseScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun preview() {
        snapshotTestRule.snapshots {
            MedicationUseScreenPreview()
        }
    }
}
