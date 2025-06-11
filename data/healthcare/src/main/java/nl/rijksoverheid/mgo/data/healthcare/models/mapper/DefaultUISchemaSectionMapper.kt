package nl.rijksoverheid.mgo.data.healthcare.models.mapper

import androidx.annotation.VisibleForTesting
import nl.rijksoverheid.mgo.data.fhirParser.models.DownloadBinary
import nl.rijksoverheid.mgo.data.fhirParser.models.DownloadLink
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.fhirParser.models.MultipleGroupedValues
import nl.rijksoverheid.mgo.data.fhirParser.models.MultipleValues
import nl.rijksoverheid.mgo.data.fhirParser.models.ReferenceLink
import nl.rijksoverheid.mgo.data.fhirParser.models.ReferenceValue
import nl.rijksoverheid.mgo.data.fhirParser.models.SingleValue
import nl.rijksoverheid.mgo.data.fhirParser.models.UiElement
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.UiSchemaMapper
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.MgoResourceRepository
import nl.rijksoverheid.mgo.data.healthcare.models.UISchemaRow
import nl.rijksoverheid.mgo.data.healthcare.models.UISchemaSection
import javax.inject.Inject

@VisibleForTesting
class DefaultUISchemaSectionMapper
  @Inject
  constructor(
    private val mgoResourceRepository: MgoResourceRepository,
    private val uiSchemaMapper: UiSchemaMapper,
  ) : UISchemaSectionMapper {
    override suspend fun map(uiSchema: HealthUiSchema): List<UISchemaSection> =
      uiSchema.children.map { uiSchemaChild ->
        UISchemaSection(
          heading = uiSchemaChild.label,
          rows = uiSchemaChild.children.mapNotNull { uiElement -> uiElement.toRow() },
        )
      }

    @VisibleForTesting
    suspend fun UiElement.toRow(): UISchemaRow? =
      when (this) {
        is ReferenceLink -> {
          if (isReferenceClickable(reference)) {
            UISchemaRow.Reference(heading = null, value = this.label, referenceId = this.reference)
          } else {
            UISchemaRow.Static(heading = this.label, value = this.reference)
          }
        }

        is DownloadLink ->
          this.url?.let { url ->
            UISchemaRow.Link(heading = null, value = this.label, url = url)
          }

        is SingleValue -> {
          this.display?.let { display ->
            UISchemaRow.Static(heading = this.label, value = display)
          }
        }

        is MultipleValues -> {
          this.display?.let { display ->
            UISchemaRow.Static(heading = this.label, value = display.joinToString(", "))
          }
        }

        is MultipleGroupedValues -> {
          this.display?.let { display ->
            UISchemaRow.Static(heading = this.label, value = display.flatten().joinToString(", "))
          }
        }

        is ReferenceValue -> {
          val reference = this.reference
          val display = this.display
          if (reference != null && display != null) {
            if (isReferenceClickable(reference)) {
              UISchemaRow.Reference(heading = this.label, value = display, referenceId = reference)
            } else {
              UISchemaRow.Static(heading = this.label, value = display)
            }
          } else {
            null
          }
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

    /**
     * Currently we only support references that are already locally present.
     * We don't want to show a row as clickable if it isn't, so this function makes sure
     * that the ui schema is present that the row links to.
     */
    private suspend fun isReferenceClickable(reference: String): Boolean =
      mgoResourceRepository.get(reference).getOrNull()?.let { mgoResource ->
        try {
          uiSchemaMapper.getDetail(mgoResource)
          true
        } catch (e: Exception) {
          false
        }
      } ?: false
  }
