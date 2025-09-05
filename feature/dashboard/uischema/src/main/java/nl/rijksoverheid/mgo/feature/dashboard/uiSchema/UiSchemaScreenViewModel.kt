package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.UiSchemaMapper
import nl.rijksoverheid.mgo.data.healthcare.binary.FhirBinaryRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.MgoResourceRepository
import nl.rijksoverheid.mgo.data.healthcare.models.UISchemaRow
import nl.rijksoverheid.mgo.data.healthcare.models.mapper.UISchemaSectionMapper
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.getDocumentsResourceEndpoint
import timber.log.Timber

/**
 * The [ViewModel] for [UiSchemaScreen].
 *
 * @param organization The [MgoOrganization] for the health care data.
 * @param mgoResource The [MgoResource] to get the health care data from.
 * @param isSummary If this screen shows a summary of the health care data, or the complete set.
 * @param fhirBinaryRepository The [FhirBinaryRepository] to download files.
 * @param uiSchemaMapper The [UiSchemaMapper] to map [MgoResource] to [HealthUiSchema].
 * @param mgoResourceRepository The [MgoResourceRepository] to get new [MgoResource] from.
 * @param uiSchemaSectionMapper The [UISchemaSectionMapper] to map [HealthUiSchema] to [UiSchemaSection].
 */
@HiltViewModel(assistedFactory = UiSchemaScreenViewModel.Factory::class)
internal class UiSchemaScreenViewModel
  @AssistedInject
  constructor(
    @Assisted val organization: MgoOrganization,
    @Assisted private val mgoResource: MgoResource,
    @Assisted private val isSummary: Boolean,
    private val fhirBinaryRepository: FhirBinaryRepository,
    private val uiSchemaMapper: UiSchemaMapper,
    private val mgoResourceRepository: MgoResourceRepository,
    private val uiSchemaSectionMapper: UISchemaSectionMapper,
  ) : ViewModel() {
    @AssistedFactory
    interface Factory {
      fun create(
        organization: MgoOrganization,
        mgoResource: MgoResource,
        isSummary: Boolean,
      ): UiSchemaScreenViewModel
    }

    private val _navigate = MutableSharedFlow<MgoResource>(extraBufferCapacity = 1)
    val navigate = _navigate.asSharedFlow()

    private val _viewState = MutableStateFlow(UiSchemaScreenViewState(toolbarTitle = "", sections = listOf()))
    val viewState = _viewState.asStateFlow()

    /**
     * Get the [HealthUiSchema] from a [MgoResource] to be able to display health care data.
     */
    init {
      viewModelScope.launch {
        val uiSchema =
          if (isSummary) {
            uiSchemaMapper.getSummary(
              healthCareOrganizationName = organization.name,
              mgoResource = mgoResource,
            )
          } else {
            uiSchemaMapper.getDetail(
              healthCareOrganizationName = organization.name,
              mgoResource = mgoResource,
            )
          }
        val uiSchemaSections = uiSchemaSectionMapper.map(uiSchema)
        _viewState.update { viewState ->
          viewState.copy(toolbarTitle = uiSchema.label, sections = uiSchemaSections)
        }
      }
    }

    /**
     * When clicking on a reference, get the [MgoResource] and navigate to the [UiSchemaScreen] to show the new health care data.
     * @param row The clicked reference row.
     */
    fun onClickReferenceRow(row: UISchemaRow.Reference) {
      viewModelScope.launch {
        mgoResourceRepository
          .get(row.referenceId)
          .onSuccess { mgoResource ->
            _navigate.tryEmit(mgoResource)
          }.onFailure { error ->
            Timber.e(error, "Failed to get mgo resource")
          }
      }
    }

    /**
     * When clicking on a file, download the binary and update the view state to reflect the state of downloading.
     * @param row The clicked file row.
     */
    fun onClickFileRow(row: UISchemaRow.Binary.NotDownloaded) {
      viewModelScope.launch {
        // This organization should have a document resource endpoint to get the binary from
        val endpoint = organization.getDocumentsResourceEndpoint() ?: return@launch

        // Set loading state
        val loadingRow = UISchemaRow.Binary.Loading(heading = row.heading, value = row.value)
        updateRow(loadingRow)

        // Download file
        fhirBinaryRepository
          .download(resourceEndpoint = endpoint, fhirBinary = row.binary)
          .onSuccess { binary ->
            val downloadedRow = UISchemaRow.Binary.Downloaded(heading = row.heading, value = row.value, binary = binary)
            updateRow(downloadedRow)
          }.onFailure { error ->
            Timber.e(error, "Failed to download binary")
            val errorRow = UISchemaRow.Binary.NotDownloaded.Error(heading = row.heading, value = row.value, binary = row.binary)
            updateRow(errorRow)
          }
      }
    }

    /**
     * Update a row in the view state. Matches based on the value of the [UISchemaRow].
     * @param newRow The row to be updated.
     */
    private fun updateRow(newRow: UISchemaRow) {
      _viewState.update { viewState ->
        viewState.copy(
          sections =
            viewState.sections.map { section ->
              val rows =
                section.rows.map { oldRow ->
                  if (oldRow.value == newRow.value) {
                    newRow
                  } else {
                    oldRow
                  }
                }
              section.copy(
                rows = rows,
              )
            },
        )
      }
    }
  }
