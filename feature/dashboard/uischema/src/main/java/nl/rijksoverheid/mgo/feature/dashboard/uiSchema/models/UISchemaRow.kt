package nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models

import nl.rijksoverheid.mgo.data.fhirParser.models.DownloadBinary
import nl.rijksoverheid.mgo.data.fhirParser.models.DownloadLink
import nl.rijksoverheid.mgo.data.fhirParser.models.MultipleGroupedValues
import nl.rijksoverheid.mgo.data.fhirParser.models.MultipleValues
import nl.rijksoverheid.mgo.data.fhirParser.models.ReferenceLink
import nl.rijksoverheid.mgo.data.fhirParser.models.ReferenceValue
import nl.rijksoverheid.mgo.data.fhirParser.models.SingleValue
import nl.rijksoverheid.mgo.data.fhirParser.models.UiElement
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

internal fun UiElement.toRow(): UISchemaRow {
    return when (this) {
        is ReferenceLink -> {
            UISchemaRow.Reference(heading = null, value = this.label, referenceId = this.reference)
        }
        is DownloadLink -> {
            val url = this.url
            if (url == null) {
                UISchemaRow.File.Empty(heading = null, value = this.label)
            } else {
                UISchemaRow.File.NotDownloaded.Idle(heading = null, value = this.label, binary = url)
            }
        }
        is SingleValue -> {
            UISchemaRow.Static(heading = this.label, value = this.display ?: "")
        }
        is MultipleValues -> {
            UISchemaRow.Static(heading = this.label, value = this.display?.joinToString(", ") ?: "")
        }
        is MultipleGroupedValues -> {
            UISchemaRow.Static(heading = this.label, value = this.display?.joinToString(", ") ?: "")
        }
        is ReferenceValue -> {
            UISchemaRow.Static(heading = this.label, value = this.display ?: "")
        }
        is DownloadBinary -> {
            UISchemaRow.Static(heading = this.label, value = this.label)
        }
    }
}
