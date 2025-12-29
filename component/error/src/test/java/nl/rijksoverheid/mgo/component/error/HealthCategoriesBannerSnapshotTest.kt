package nl.rijksoverheid.mgo.component.error

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class HealthCategoriesBannerSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun loading() {
    snapshotTestRule.snapshots {
      HealthCategoriesBannerLoadingPreview()
    }
  }

  @Test
  fun userError() {
    snapshotTestRule.snapshots {
      HealthCategoriesBannerUserErrorPreview()
    }
  }

  @Test
  fun userErrorPartial() {
    snapshotTestRule.snapshots {
      HealthCategoriesBannerUserErrorPartialPreview()
    }
  }

  @Test
  fun serverError() {
    snapshotTestRule.snapshots {
      HealthCategoriesBannerServerErrorPreview()
    }
  }

  @Test
  fun serverErrorPartial() {
    snapshotTestRule.snapshots {
      HealthCategoriesBannerServerErrorPartialPreview()
    }
  }
}
