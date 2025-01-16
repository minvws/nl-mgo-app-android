package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import nl.rijksoverheid.mgo.data.healthcare.binary.FhirBinary

internal sealed class AttachmentState {
    data object NotDownloaded : AttachmentState()

    data object Loading : AttachmentState()

    data object Empty : AttachmentState()

    data class Error(val error: Throwable) : AttachmentState()

    data class Downloaded(val binary: FhirBinary) : AttachmentState()
}
