package nl.rijksoverheid.mgo.feature.localisation.organizationList.manual

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class OrganizationListManualScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun loading() {
    snapshotTestRule.snapshots {
      OrganizationListManualScreenLoadingPreview()
    }
  }

  @Test
  fun empty() {
    snapshotTestRule.snapshots {
      OrganizationListManualScreenEmptyPreview()
    }
  }

  @Test
  fun searchResults() {
    snapshotTestRule.snapshots {
      OrganizationListManualScreenResultsPreview()
    }
  }

  @Test
  fun error() {
    snapshotTestRule.snapshots {
      OrganizationListManualScreenErrorPreview()
    }
  }
}
