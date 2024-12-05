package nl.rijksoverheid.mgo.component.theme.composable

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MgoCheckboxSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun checked() {
        snapshotTestRule.snapshots {
            MgoCheckboxCheckedPreview()
        }
    }

    @Test
    fun unChecked() {
        snapshotTestRule.snapshots {
            MgoCheckboxUnCheckedPreview()
        }
    }
}
