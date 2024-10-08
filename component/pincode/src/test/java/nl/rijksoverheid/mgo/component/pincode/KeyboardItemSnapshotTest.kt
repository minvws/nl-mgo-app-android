package nl.rijksoverheid.mgo.component.pincode

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class KeyboardItemSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun icon() {
        snapshotTestRule.snapshots {
            KeyboardItemIconPreview()
        }
    }

    @Test
    fun number() {
        snapshotTestRule.snapshots {
            KeyboardItemNumberPreview()
        }
    }
}
