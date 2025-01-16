package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail.models

sealed class UISchemaRow(open val heading: String?, open val value: String) {
    data class Static(override val heading: String?, override val value: String) : UISchemaRow(heading, value)

    data class Reference(override val heading: String?, override val value: String, val referenceId: String) : UISchemaRow(heading, value)

    sealed class File(override val heading: String?, override val value: String) : UISchemaRow(heading, value) {
        sealed class NotDownloaded(override val heading: String?, override val value: String) : File(heading, value) {
            data class Idle(override val heading: String?, override val value: String, val binary: String) : NotDownloaded(heading, value)

            data class Loading(override val heading: String?, override val value: String) : NotDownloaded(heading, value)

            data class Error(override val heading: String?, override val value: String, val binary: String) : NotDownloaded(heading, value)
        }

        data class Downloaded(override val heading: String?, override val value: String, val fileName: String) : File(heading, value)
    }
}
