package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail

import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.UiSchemaAttachmentListItemDownloadedPreview
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.UiSchemaAttachmentListItemEmptyPreview
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.UiSchemaAttachmentListItemErrorPreview
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.UiSchemaAttachmentListItemLoadingPreview
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.UiSchemaAttachmentListItemNotDownloadedPreview
import nl.rijksoverheid.mgo.framework.snapshots.SnapshotTestRule
import org.junit.Rule
import org.junit.Test

internal class UiSchemaAttachmentListItemSnapshotTest {
    @get:Rule
    val snapshotTestRule = SnapshotTestRule()

    @Test
    fun notDownloaded() {
        snapshotTestRule.snapshots {
            UiSchemaAttachmentListItemNotDownloadedPreview()
        }
    }

    @Test
    fun downloaded() {
        snapshotTestRule.snapshots {
            UiSchemaAttachmentListItemDownloadedPreview()
        }
    }

    @Test
    fun loading() {
        snapshotTestRule.snapshots {
            UiSchemaAttachmentListItemLoadingPreview()
        }
    }

    @Test
    fun empty() {
        snapshotTestRule.snapshots {
            UiSchemaAttachmentListItemEmptyPreview()
        }
    }

    @Test
    fun error() {
        snapshotTestRule.snapshots {
            UiSchemaAttachmentListItemErrorPreview()
        }
    }
}
