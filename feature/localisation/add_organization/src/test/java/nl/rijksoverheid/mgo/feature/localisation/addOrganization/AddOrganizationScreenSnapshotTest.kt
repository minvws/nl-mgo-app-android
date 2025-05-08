package nl.rijksoverheid.mgo.feature.localisation.addOrganization

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class AddOrganizationScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun launchView() {
    snapshotTestRule.snapshots {
      AddOrganizationScreenPreview()
    }
  }
}
