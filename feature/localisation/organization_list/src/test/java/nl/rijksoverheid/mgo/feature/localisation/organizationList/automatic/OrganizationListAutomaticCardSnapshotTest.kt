package nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotDevices
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class OrganizationListAutomaticCardSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun add() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      OrganizationListAutomaticCardAddPreview()
    }
  }

  @Test
  fun added() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      OrganizationListAutomaticCardAddedPreview()
    }
  }

  @Test
  fun notSupported() {
    snapshotTestRule.snapshots(SnapshotDevices.PhoneLightDarkPortrait) {
      OrganizationAutomaticSearchCardNotSupportedPreview()
    }
  }
}
