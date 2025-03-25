package nl.rijksoverheid.mgo.feature.settings.home

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class SettingsHomeScreenSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun withBiometric() {
        snapshotTestRule.snapshots {
            SettingsHomeScreenWithBiometricPreview()
        }
    }

    @Test
    fun withoutBiometric() {
        snapshotTestRule.snapshots {
            SettingsHomeScreenWithoutBiometricPreview()
        }
    }
}
