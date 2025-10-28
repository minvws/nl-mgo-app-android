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
        is ReferenceLink -> {
          if (isReferenceClickable(reference)) {
            UISchemaRow.Reference(
              heading = null,
              value = this.label,
              referenceId = this.reference,
            )
          } else {
            UISchemaRow.Static(
              heading = this.label,
              value = listOf(UISchemaRowStaticValue(value = this.reference)),
            )
          }
        }

        is DownloadLink ->
          this.url?.let { url ->
            UISchemaRow.Link(
              heading = null,
              value = this.label,
              url = url,
            )
          }

        is SingleValue -> {
          this.value?.display?.let { display ->
            UISchemaRow.Static(
              heading = this.label,
              value = listOf(UISchemaRowStaticValue(value = display, snomedCode = this.value?.getSnomedCode())),
            )
          }
        }

        is MultipleValues -> {
          this.value?.let { displayValues ->
            UISchemaRow.Static(
              heading = this.label,
              value =
                displayValues.mapNotNull { displayValue ->
                  val display = displayValue.display ?: return@mapNotNull null
                  UISchemaRowStaticValue(value = display, snomedCode = displayValue.getSnomedCode())
                },
            )
          }
        }

        is MultipleGroupedValues -> {
          this.value?.flatten()?.let { displayValues ->
            UISchemaRow.Static(
              heading = this.label,
              value =
                displayValues.mapNotNull { displayValue ->
                  val display = displayValue.display ?: return@mapNotNull null
                  UISchemaRowStaticValue(value = display, snomedCode = displayValue.getSnomedCode())
                },
            )
          }
        }

        is ReferenceValue -> {
          val reference = this.reference
          val display = this.display
          if (reference != null && display != null) {
            if (isReferenceClickable(reference)) {
              UISchemaRow.Reference(
                heading = this.label,
                value = display,
                referenceId = reference,
              )
            } else {
              UISchemaRow.Static(
                heading = this.label,
                value = listOf(UISchemaRowStaticValue(value = display)),
              )
            }
          } else {
            null
          }
        }

        is DownloadBinary -> {
          val reference = this.reference
          if (reference == null) {
            UISchemaRow.Binary.Empty(
              heading = null,
              value = this.label,
            )
          } else {
            UISchemaRow.Binary.NotDownloaded.Idle(
              heading = null,
              value = this.label,
              binary = reference,
            )
          }
        }
      }

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
