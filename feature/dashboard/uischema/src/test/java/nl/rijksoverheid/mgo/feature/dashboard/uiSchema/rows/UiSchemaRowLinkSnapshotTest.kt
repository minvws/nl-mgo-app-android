package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class UiSchemaRowLinkSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun preview() {
    snapshotTestRule.snapshots {
      UiSchemaRowLinkPreview()
    }
  }
}
