package nl.rijksoverheid.mgo.component.theme.composable

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MgoButtonSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun primary() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoButtonPrimaryPreview()
        }
    }

    @Test
    fun secondary() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoButtonSecondaryPreview()
        }
    }
}
