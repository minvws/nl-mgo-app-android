package nl.rijksoverheid.mgo.component.error

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class ErrorBannerSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun loading() {
    snapshotTestRule.snapshots {
      ErrorBannerLoadingPreview()
    }
  }

  @Test
  fun userError() {
    snapshotTestRule.snapshots {
      ErrorBannerUserErrorPreview()
    }
  }

  @Test
  fun userErrorPartial() {
    snapshotTestRule.snapshots {
      ErrorBannerUserErrorPartialPreview()
    }
  }

  @Test
  fun serverError() {
    snapshotTestRule.snapshots {
      ErrorBannerServerErrorPreview()
    }
  }

  @Test
  fun serverErrorPartial() {
    snapshotTestRule.snapshots {
      ErrorBannerServerErrorPartialPreview()
    }
  }
}
