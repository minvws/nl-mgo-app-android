package nl.rijksoverheid.mgo.feature.healthcareprovider.medication

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MedicationScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun loading() {
        snapshotTestRule.snapshots {
            MedicationScreenLoadingPreview()
        }
    }

    @Test
    fun medications() {
        snapshotTestRule.snapshots {
            MedicationScreenMedicationsPreview()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots {
            MedicationScreenErrorPreview()
        }
    }
}
