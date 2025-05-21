package nl.rijksoverheid.mgo.feature.localisation.organizationList.automatic

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class OrganizationListAutomaticScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun loading() {
    snapshotTestRule.snapshots {
      OrganizationListAutomaticSearchScreenLoadingPreview()
    }
  }

  @Test
  fun searchResults() {
    snapshotTestRule.snapshots {
      OrganizationListAutomaticSearchScreenSearchResultsPreview()
    }
  }

  @Test
  fun error() {
    snapshotTestRule.snapshots {
      OrganizationListAutomaticSearchScreenErrorPreview()
    }
  }
}
