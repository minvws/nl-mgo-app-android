package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class UiSchemaRowReferenceSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun withHeading() {
    snapshotTestRule.snapshots {
      UiSchemaRowReferenceWithHeadingPreview()
    }
  }

  @Test
  fun withoutHeading() {
    snapshotTestRule.snapshots {
      UiSchemaRowReferenceWithoutHeadingPreview()
    }
  }
}
