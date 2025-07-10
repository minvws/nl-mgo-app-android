package nl.rijksoverheid.mgo.data.healthcare.models

import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceReferenceId
import nl.rijksoverheid.mgo.data.fhirParser.models.DownloadBinary
import nl.rijksoverheid.mgo.data.fhirParser.models.DownloadLink
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.fhirParser.models.MultipleGroupedValues
import nl.rijksoverheid.mgo.data.fhirParser.models.MultipleValues
import nl.rijksoverheid.mgo.data.fhirParser.models.ReferenceLink
import nl.rijksoverheid.mgo.data.fhirParser.models.ReferenceValue
import nl.rijksoverheid.mgo.data.fhirParser.models.SingleValue
import nl.rijksoverheid.mgo.data.fhirParser.models.UiElement

/**
 * Represents a list item that is build from a [HealthUiSchema].
 *
 * @param heading The top text of the list item.
 * @param value The bottom text of the list item.
 */
sealed class UISchemaRow(
  open val heading: String?,
  open val value: String,
) {
  /**
   * Represents a non clickable list item.
   *
   * @param heading The top text of the list item.
   * @param value The bottom text of the list item.
   */
  data class Static(
    override val heading: String?,
    override val value: String,
  ) : UISchemaRow(heading, value)

  /**
   * Represents a clickable list item that links to a [MgoResource].
   *
   * @param heading The top text of the list item.
   * @param value The bottom text of the list item.
   * @param referenceId The [MgoResourceReferenceId] of the [MgoResource] to link to.
   */
  data class Reference(
    override val heading: String?,
    override val value: String,
    val referenceId: String,
  ) : UISchemaRow(heading, value)

  /**
   * Represents a clickable list item that can download a file from a FHIR binary resource.
   *
   * @param heading The top text of the list item.
   * @param value The bottom text of the list item.
   */
  sealed class Binary(
    override val heading: String?,
    override val value: String,
  ) : UISchemaRow(heading, value) {
    /**
     * Represents the state that a file has not been downloaded (yet).
     *
     * @param heading The top text of the list item.
     * @param value The bottom text of the list item.
     * @param binary A string to use in [FhirBinaryRepository] to get the binary.
     */
    sealed class NotDownloaded(
      override val heading: String?,
      override val value: String,
      open val binary: String,
    ) : Binary(
        heading,
        value,
      ) {
      /**
       * Represents the state that the list item can be clicked to start downloading the file.
       *
       * @param heading The top text of the list item.
       * @param value The bottom text of the list item.
       * @param binary A string to use in [FhirBinaryRepository] to get the binary.
       */
      data class Idle(
        override val heading: String?,
        override val value: String,
        override val binary: String,
      ) : NotDownloaded(heading, value, binary)

      /**
       * Represents the state that the list item has failed to download the file.
       *
       * @param heading The top text of the list item.
       * @param value The bottom text of the list item.
       * @param binary A string to use in [FhirBinaryRepository] to get the binary.
       */
      data class Error(
        override val heading: String?,
        override val value: String,
        override val binary: String,
      ) : NotDownloaded(heading, value, binary)
    }

    /**
     * Represents the state that the list item is currently downloading the file.
     *
     * @param heading The top text of the list item.
     * @param value The bottom text of the list item.
     */
    data class Loading(
      override val heading: String?,
      override val value: String,
    ) : Binary(heading, value)

    /**
     * Represents the state that the list item should have a file, but it does not exist.
     *
     * @param heading The top text of the list item.
     * @param value The bottom text of the list item.
     */
    data class Empty(
      override val heading: String?,
      override val value: String,
    ) : Binary(heading, value)

    /**
     * Represents the state that the list item has downloaded the file. Clicking it should open the file.
     *
     * @param heading The top text of the list item.
     * @param value The bottom text of the list item.
     * @param binary The [FhirBinary] containing the downloaded file.
     */
    data class Downloaded(
      override val heading: String?,
      override val value: String,
      val binary: FhirBinary,
    ) : Binary(heading, value)
  }

  /**
   * Represents a clickable list item that can download a file from a link.
   *
   * @param heading The top text of the list item.
   * @param value The bottom text of the list item.
   * @param url The url to download the file.
   */
  data class Link(
    override val heading: String?,
    override val value: String,
    val url: String,
  ) : UISchemaRow(heading, value)
}

fun UiElement.toRow(): UISchemaRow =
  when (this) {
    is ReferenceLink -> {
      UISchemaRow.Reference(heading = null, value = this.label, referenceId = this.reference)
    }

    is DownloadLink -> {
      UISchemaRow.Link(heading = null, value = this.label, this.url ?: "")
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
      UISchemaRow.Reference(
        heading = this.label,
        value = this.display ?: "",
        referenceId = this.reference ?: "",
      )
    }

    is DownloadBinary -> {
      val reference = this.reference
      if (reference == null) {
        UISchemaRow.Binary.Empty(heading = null, value = this.label)
      } else {
        UISchemaRow.Binary.NotDownloaded.Idle(
          heading = null,
          value = this.label,
          binary = reference,
        )
      }
    }
  }
