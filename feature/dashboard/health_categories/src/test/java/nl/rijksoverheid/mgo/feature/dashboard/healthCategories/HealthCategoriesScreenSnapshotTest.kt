package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class HealthCategoriesScreenSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun withProviders() {
    snapshotTestRule.snapshots {
      OverviewScreenWithProvidersPreview()
    }
  }

  @Test
  fun noProviders() {
    snapshotTestRule.snapshots {
      OverviewScreenNoProvidersPreview()
    }
  }

  @Test
  fun withFavorites() {
    snapshotTestRule.snapshots {
      OverviewScreenWithProvidersAndFavoritesPreview()
    }
  }
}
