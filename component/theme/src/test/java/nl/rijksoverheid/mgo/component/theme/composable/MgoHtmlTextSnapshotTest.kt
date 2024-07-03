package nl.rijksoverheid.mgo.component.theme.composable

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MgoHtmlTextSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun preview() {
        snapshotTestRule.snapshots {
            MgoHtmlTextPreview()
        }
    }
}
