package nl.rijksoverheid.mgo.feature.organization.labResults

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class LabResultsScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun loading() {
        snapshotTestRule.snapshots {
            LabResultsScreenLoadingPreview()
        }
    }

    @Test
    fun concerns() {
        snapshotTestRule.snapshots {
            LabResultsScreenTestResultsPreview()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots {
            LabResultsScreenErrorPreview()
        }
    }
}
