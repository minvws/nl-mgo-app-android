package nl.rijksoverheid.mgo.component.uiSchema

import nl.rijksoverheid.mgo.data.fhir.FhirBinary

sealed class UISchemaRow(
  open val heading: String?,
  open val displayValue: String,
) {
  data class Static(
    override val heading: String?,
    val value: List<UISchemaRowStaticValue>,
  ) : UISchemaRow(heading, value.joinToString(", "))

  data class Reference(
    override val heading: String?,
    val value: String,
    val referenceId: String,
  ) : UISchemaRow(heading, value)

  sealed class Binary(
    override val heading: String?,
    open val value: String,
  ) : UISchemaRow(heading, value) {
    sealed class NotDownloaded(
      override val heading: String?,
      override val value: String,
      open val binary: String,
    ) : Binary(
        heading,
        value,
      ) {
      data class Idle(
        override val heading: String?,
        override val value: String,
        override val binary: String,
      ) : NotDownloaded(heading, value, binary)

      data class Error(
        override val heading: String?,
        override val value: String,
        override val binary: String,
      ) : NotDownloaded(heading, value, binary)
    }

    data class Loading(
      override val heading: String?,
      override val value: String,
    ) : Binary(heading, value)

    data class Empty(
      override val heading: String?,
      override val value: String,
    ) : Binary(heading, value)

    data class Downloaded(
      override val heading: String?,
      override val value: String,
      val binary: FhirBinary,
    ) : Binary(heading, value)
  }

  data class Link(
    override val heading: String?,
    val value: String,
    val url: String,
  ) : UISchemaRow(heading, value)
}

data class UISchemaRowStaticValue(
  val value: String,
  val snomedCode: String? = null,
)
