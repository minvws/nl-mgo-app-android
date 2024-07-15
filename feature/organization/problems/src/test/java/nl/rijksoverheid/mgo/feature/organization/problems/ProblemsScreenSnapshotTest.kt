package nl.rijksoverheid.mgo.feature.organization.problems

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class ProblemsScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun loading() {
        snapshotTestRule.snapshots {
            ProblemsScreenLoadingPreview()
        }
    }

    @Test
    fun concerns() {
        snapshotTestRule.snapshots {
            ProblemsScreenConcernsPreview()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots {
            ProblemsScreenErrorPreview()
        }
    }
}
