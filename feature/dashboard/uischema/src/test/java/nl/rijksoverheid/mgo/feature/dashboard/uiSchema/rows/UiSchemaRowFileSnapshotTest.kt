package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.rows

import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class UiSchemaRowFileSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun idle() {
        snapshotTestRule.snapshots {
            UiSchemaRowFileIdlePreview()
        }
    }

    @Test
    fun loading() {
        snapshotTestRule.snapshots {
            UiSchemaRowFileLoadingPreview()
        }
    }

    @Test
    fun downloaded() {
        snapshotTestRule.snapshots {
            UiSchemaRowFileDownloadedPreview()
        }
    }

    @Test
    fun empty() {
        snapshotTestRule.snapshots {
            UiSchemaRowFileEmptyPreview()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots {
            UiSchemaRowFileErrorPreview()
        }
    }
}
