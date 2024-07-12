package nl.rijksoverheid.mgo.feature.localisation.organizationSearch

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class OrganizationSearchCardSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun added() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            OrganizationSearchCardAddedPreview()
        }
    }

    @Test
    fun notAdded() {
        snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
            OrganizationSearchCardNotAddedPreview()
        }
    }
}
