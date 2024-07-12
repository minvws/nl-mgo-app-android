package nl.rijksoverheid.mgo.feature.healthcareprovider.problems

import nl.rijksoverheid.mgo.feature.organization.problems.ProblemsScreenConcernsPreview
import nl.rijksoverheid.mgo.feature.organization.problems.ProblemsScreenErrorPreview
import nl.rijksoverheid.mgo.feature.organization.problems.ProblemsScreenLoadingPreview
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
