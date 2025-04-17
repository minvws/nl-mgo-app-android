package nl.rijksoverheid.mgo.feature.dashboard.organizations

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class OrganizationsScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun withOrganizations() {
    snapshotTestRule.snapshots {
      OrganizationsScreenWithOrganizationsPreview()
    }
  }

  @Test
  fun noOrganizations() {
    snapshotTestRule.snapshots {
      OrganizationsScreenNoOrganizationsPreview()
    }
  }
}
