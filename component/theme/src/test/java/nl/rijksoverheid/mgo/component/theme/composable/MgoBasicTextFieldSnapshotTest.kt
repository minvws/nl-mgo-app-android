package nl.rijksoverheid.mgo.component.theme.composable

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MgoBasicTextFieldSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun empty() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoBasicTextFieldEmptyPreview()
        }
    }

    @Test
    fun filled() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoBasicTextFieldFilledPreview()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoBasicTextFieldErrorPreview()
        }
    }
}
