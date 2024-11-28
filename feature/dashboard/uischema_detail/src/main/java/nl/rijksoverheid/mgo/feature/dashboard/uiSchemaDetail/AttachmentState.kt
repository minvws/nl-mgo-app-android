package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail

import java.io.File

internal sealed class AttachmentState(open val label: String) {
    data class NotDownloaded(override val label: String, val url: String) : AttachmentState(label)

    data class Loading(override val label: String) : AttachmentState(label)

    data class Downloaded(override val label: String, val file: File, val contentType: String) : AttachmentState(label)
}
