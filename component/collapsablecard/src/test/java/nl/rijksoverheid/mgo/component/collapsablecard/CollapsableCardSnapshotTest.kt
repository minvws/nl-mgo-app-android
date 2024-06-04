package nl.rijksoverheid.mgo.component.collapsablecard

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class CollapsableCardSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun preview() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            CollapsableCardPreview()
        }
    }
}
