package nl.rijksoverheid.mgo.component.snackbar

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MgoSnackBarSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun success() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoSnackBarSuccess()
        }
    }

    @Test
    fun successWithAction() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoSnackBarSuccessWithAction()
        }
    }

    @Test
    fun successOverflow() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoSnackBarSuccessOverflow()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoSnackBarError()
        }
    }

    @Test
    fun warning() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoSnackBarWarning()
        }
    }

    @Test
    fun warningWithAction() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoSnackBarWarningWithAction()
        }
    }

    @Test
    fun info() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoSnackBarInfo()
        }
    }
}
