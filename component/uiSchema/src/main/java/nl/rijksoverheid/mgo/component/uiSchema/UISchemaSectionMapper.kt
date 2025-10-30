package nl.rijksoverheid.mgo.component.uiSchema

import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceReferenceId
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DisplayValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DownloadBinary
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.DownloadLink
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MultipleGroupedValues
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.MultipleValues
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ReferenceLink
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.ReferenceValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.SingleValue
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.UiElement
import javax.inject.Inject

class UISchemaSectionMapper
  @Inject
  constructor(
    private val mgoResourceStore: MgoResourceStore,
  ) {
    fun map(uiSchema: HealthUiSchema): List<UISchemaSection> =
      uiSchema.children.map { uiSchemaChild ->
        UISchemaSection(
          heading = uiSchemaChild.label,
          rows = uiSchemaChild.children.mapNotNull { uiElement -> uiElement.toRow() },
        )
      }

    private fun UiElement.toRow(): UISchemaRow? =
      when (this) {
        is DownloadBinary -> mapDownloadBinary(this)
        is DownloadLink -> mapDownloadLink(this)
        is MultipleGroupedValues -> mapMultipleGroupedValues(this)
        is MultipleValues -> mapMultipleValues(this)
        is ReferenceLink -> mapReferenceLink(this)
        is ReferenceValue -> mapReferenceValue(this)
        is SingleValue -> mapSingleValue(this)
      }

    private fun mapDownloadBinary(uiElement: DownloadBinary): UISchemaRow {
      val heading = uiElement.label
      val reference = uiElement.reference

      return if (reference != null) {
        UISchemaRow.Binary.NotDownloaded.Idle(
          heading = heading,
          value = heading,
          binary = reference,
        )
      } else {
        UISchemaRow.Binary.Empty(
          heading = heading,
          value = heading,
        )
      }
    }

    private fun mapDownloadLink(uiElement: DownloadLink): UISchemaRow? {
      val url = uiElement.url ?: return null

      return UISchemaRow.Link(
        heading = uiElement.label,
        value = uiElement.label,
        url = url,
      )
    }

    private fun mapMultipleGroupedValues(uiElement: MultipleGroupedValues): UISchemaRow? {
      val groupedValues = uiElement.value ?: return null
      val flattenedValues = groupedValues.flatten()
      val staticValues = flattenedValues.mapNotNull { it.toStaticValue() }
      if (staticValues.isEmpty()) return null

      return UISchemaRow.Static(
        heading = uiElement.label,
        value = staticValues,
      )
    }

    private fun mapMultipleValues(uiElement: MultipleValues): UISchemaRow? {
      val values = uiElement.value ?: return null
      val staticValues = values.mapNotNull { it.toStaticValue() }
      if (staticValues.isEmpty()) return null

      return UISchemaRow.Static(
        heading = uiElement.label,
        value = staticValues,
      )
    }

    private fun mapReferenceLink(uiElement: ReferenceLink): UISchemaRow =
      if (isReferenceClickable(uiElement.reference)) {
        UISchemaRow.Reference(
          heading = null,
          value = uiElement.label,
          referenceId = uiElement.reference,
        )
      } else {
        UISchemaRow.Static(
          heading = uiElement.label,
          value = listOf(UISchemaRowStaticValue(value = uiElement.reference)),
        )
      }

    private fun mapReferenceValue(uiElement: ReferenceValue): UISchemaRow? {
      val reference = uiElement.reference
      val display = uiElement.display
      if (reference != null && display != null) {
        return if (isReferenceClickable(reference)) {
          UISchemaRow.Reference(
            heading = uiElement.label,
            value = display,
            referenceId = reference,
          )
        } else {
          UISchemaRow.Static(
            heading = uiElement.label,
            value = listOf(UISchemaRowStaticValue(value = display)),
          )
        }
      } else {
        return null
      }
    }

    private fun mapSingleValue(uiElement: SingleValue): UISchemaRow? {
      val value = uiElement.value ?: return null
      val display = value.display ?: return null

      val staticValue =
        UISchemaRowStaticValue(
          value = display,
          snomedCode = value.getSnomedCode(),
        )

      return UISchemaRow.Static(
        heading = uiElement.label,
        value = listOf(staticValue),
      )
    }

    private fun DisplayValue?.toStaticValue(): UISchemaRowStaticValue? = this?.display?.let { UISchemaRowStaticValue(it, getSnomedCode()) }

    private fun DisplayValue.getSnomedCode(): String? =
      if (system == "http://snomed.info/sct") {
        code
      } else {
        null
      }

    /**
     * Currently we only support references that are already locally present.
     * We don't want to show a row as clickable if it isn't, so this function makes sure
     * that the ui schema is present that the row links to.
     */
    private fun isReferenceClickable(referenceId: MgoResourceReferenceId): Boolean =
      try {
        mgoResourceStore.get(referenceId)
        true
      } catch (e: Exception) {
        false
      }
  }
