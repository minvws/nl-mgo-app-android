package nl.rijksoverheid.mgo.feature.healthcareprovider.laboratoryTestResult

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class LaboratoryScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun loading() {
        snapshotTestRule.snapshots {
            LaboratoryTestResultScreenLoadingPreview()
        }
    }

    @Test
    fun concerns() {
        snapshotTestRule.snapshots {
            LaboratoryTestResultScreenTestResultsPreview()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots {
            LaboratoryTestResultScreenErrorPreview()
        }
    }
}
