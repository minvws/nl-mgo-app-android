package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class UiSchemaRowBinarySnapshotTest {
  @get:Rule
  val snapshotTestRule = SnapshotTestRule()

  @Test
  fun idle() {
    snapshotTestRule.snapshots {
      UiSchemaRowBinaryIdlePreview()
    }
  }

  @Test
  fun loading() {
    snapshotTestRule.snapshots {
      UiSchemaRowBinaryLoadingPreview()
    }
  }

  @Test
  fun downloaded() {
    snapshotTestRule.snapshots {
      UiSchemaRowBinaryDownloadedPreview()
    }
  }

  @Test
  fun empty() {
    snapshotTestRule.snapshots {
      UiSchemaRowBinaryEmptyPreview()
    }
  }

  @Test
  fun error() {
    snapshotTestRule.snapshots {
      UiSchemaRowBinaryErrorPreview()
    }
  }
}
