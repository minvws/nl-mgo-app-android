package nl.rijksoverheid.mgo.feature.healthcareprovider.concern

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class ConcernScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun loading() {
        snapshotTestRule.snapshots {
            ConcernScreenLoadingPreview()
        }
    }

    @Test
    fun concerns() {
        snapshotTestRule.snapshots {
            ConcernScreenConcernsPreview()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots {
            ConcernScreenErrorPreview()
        }
    }
}
