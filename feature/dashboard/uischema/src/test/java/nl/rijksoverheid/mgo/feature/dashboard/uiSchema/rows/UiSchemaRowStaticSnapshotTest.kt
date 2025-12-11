package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class UiSchemaRowStaticSnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun single() {
    snapshotTestRule.snapshots {
      UiSchemaRowStaticSingleValuePreview()
    }
  }

  @Test
  fun singleMultiLine() {
    snapshotTestRule.snapshots {
      UiSchemaRowStaticSingleValueMultiLinePreview()
    }
  }

  @Test
  fun multiple() {
    snapshotTestRule.snapshots {
      UiSchemaRowStaticMultipleValuePreview()
    }
  }
}
