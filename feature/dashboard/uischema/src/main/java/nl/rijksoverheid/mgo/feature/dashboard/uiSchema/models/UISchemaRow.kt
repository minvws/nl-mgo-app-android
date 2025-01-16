package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models

import nl.rijksoverheid.mgo.data.fhirParser.shared.UIElement
import nl.rijksoverheid.mgo.data.fhirParser.shared.UIElementType
import nl.rijksoverheid.mgo.data.healthcare.binary.FhirBinary

sealed class UISchemaRow(open val heading: String?, open val value: String) {
    data class Static(override val heading: String?, override val value: String) : UISchemaRow(heading, value)

    data class Reference(override val heading: String?, override val value: String, val referenceId: String) : UISchemaRow(heading, value)

    sealed class File(override val heading: String?, override val value: String) : UISchemaRow(heading, value) {
        sealed class NotDownloaded(override val heading: String?, override val value: String, open val binary: String) : File(
            heading,
            value,
        ) {
            data class Idle(override val heading: String?, override val value: String, override val binary: String) : NotDownloaded
                (heading, value, binary)

            data class Error(override val heading: String?, override val value: String, override val binary: String) : NotDownloaded
                (heading, value, binary)
        }

        data class Loading(override val heading: String?, override val value: String) : File(heading, value)

        data class Empty(override val heading: String?, override val value: String) : File(heading, value)

        data class Downloaded(override val heading: String?, override val value: String, val binary: FhirBinary) : File(heading, value)
    }
}

internal fun UIElement.toRow(): UISchemaRow {
    return when (this.type) {
        UIElementType.ReferenceLink -> {
            UISchemaRow.Reference(heading = null, value = this.label, referenceId = this.reference ?: "")
        }

        UIElementType.DownloadLink -> {
            val url = this.url
            if (url == null) {
                UISchemaRow.File.Empty(heading = null, value = this.label)
            } else {
                UISchemaRow.File.NotDownloaded.Idle(heading = null, value = this.label, binary = url)
            }
        }

        else -> {
            UISchemaRow.Static(heading = this.label, value = this.display.getString())
        }
    }
}
