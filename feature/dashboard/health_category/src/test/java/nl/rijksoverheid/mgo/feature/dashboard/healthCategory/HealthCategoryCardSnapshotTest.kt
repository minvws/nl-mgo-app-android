package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class HealthCategoryCardSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun preview() {
    snapshotTestRule.snapshots {
      HealthCategoryCardPreview()
    }
  }

  @Test
  fun withDetail() {
    snapshotTestRule.snapshots {
      HealthCategoryCardWithDetailPreview()
    }
  }

  @Test
  fun withDetailOverflow() {
    snapshotTestRule.snapshots {
      HealthCategoryCardWithDetailOverflowPreview()
    }
  }
}
