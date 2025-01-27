package nl.rijksoverheid.mgo.component.mgo

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class MgoButtonSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun primaryDefault() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoButtonPrimaryDefaultPreview()
        }
    }

    @Test
    fun primaryDefaultWithIcon() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoButtonPrimaryDefaultWithIconPreview()
        }
    }

    @Test
    fun primaryNegative() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoButtonPrimaryNegativePreview()
        }
    }

    @Test
    fun secondaryDefault() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoButtonSecondaryDefaultPreview()
        }
    }

    @Test
    fun secondaryNegative() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoButtonSecondaryNegativePreview()
        }
    }

    @Test
    fun tertiaryDefault() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoButtonTertiaryDefaultPreview()
        }
    }

    @Test
    fun tertiaryDefaultWithIcon() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoButtonTertiaryDefaultWithIconPreview()
        }
    }

    @Test
    fun tertiaryNegative() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            MgoButtonTertiaryNegativePreview()
        }
    }
}
