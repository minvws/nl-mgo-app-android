package nl.rijksoverheid.mgo.component.theme

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class ColumnWithButtonsSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun `not scrolling`() {
        snapshotTestRule.snapshots {
            NotScrollingPreview()
        }
    }

    @Test
    fun scrolling() {
        snapshotTestRule.snapshots {
            ScrollingPreview()
        }
    }

    @Test
    fun `two buttons`() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            TwoButtonsPreview()
        }
    }
}
