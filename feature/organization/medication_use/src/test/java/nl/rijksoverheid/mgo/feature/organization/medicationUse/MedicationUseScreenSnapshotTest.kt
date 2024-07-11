package nl.rijksoverheid.mgo.feature.healthcareprovider.medication

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MedicationUseScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun loading() {
        snapshotTestRule.snapshots {
            MedicationUseScreenLoadingPreview()
        }
    }

    @Test
    fun medications() {
        snapshotTestRule.snapshots {
            MedicationUseScreenMedicationsPreview()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots {
            MedicationUseScreenErrorPreview()
        }
    }
}
