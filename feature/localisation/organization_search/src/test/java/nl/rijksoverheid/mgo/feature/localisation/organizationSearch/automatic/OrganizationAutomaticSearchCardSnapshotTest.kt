package nl.rijksoverheid.mgo.feature.localisation.organizationSearch.automatic

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class OrganizationAutomaticSearchCardSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun add() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            OrganizationAutomaticSearchCardAddPreview()
        }
    }

    @Test
    fun added() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            OrganizationAutomaticSearchCardAddedPreview()
        }
    }

    @Test
    fun notSupported() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            OrganizationAutomaticSearchCardNotSupportedPreview()
        }
    }
}
