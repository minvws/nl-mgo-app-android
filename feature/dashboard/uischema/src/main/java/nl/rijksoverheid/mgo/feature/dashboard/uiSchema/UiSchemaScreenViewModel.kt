package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.component.organization.getDocumentsResourceEndpoint
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaRow
import nl.rijksoverheid.mgo.component.uiSchema.UISchemaSectionMapper
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceReferenceId
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceStore
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import timber.log.Timber
import javax.inject.Named

@HiltViewModel(assistedFactory = UiSchemaScreenViewModel.Factory::class)
internal class UiSchemaScreenViewModel
  @AssistedInject
  constructor(
    @Assisted val organization: MgoOrganization,
    @Assisted private val referenceId: MgoResourceReferenceId,
    @Assisted private val isSummary: Boolean,
    private val fhirRepository: FhirRepository,
    private val uiSchemaSectionMapper: UISchemaSectionMapper,
    private val uiSchemaParser: UiSchemaParser,
    private val mgoResourceStore: MgoResourceStore,
    @Named("dvaApiBaseUrl") private val dvaApiBaseUrl: String,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
  ) : ViewModel() {
    @AssistedFactory
    interface Factory {
      fun create(
        organization: MgoOrganization,
        referenceId: MgoResourceReferenceId,
        isSummary: Boolean,
      ): UiSchemaScreenViewModel
    }

    private val _navigate = MutableSharedFlow<MgoResourceReferenceId>(extraBufferCapacity = 1)
    val navigate = _navigate.asSharedFlow()

    private val _viewState = MutableStateFlow(UiSchemaScreenViewState(toolbarTitle = "", sections = listOf()))
    val viewState = _viewState.asStateFlow()

    init {
      viewModelScope.launch(ioDispatcher) {
        val mgoResource = mgoResourceStore.get(referenceId)
        val uiSchema =
          if (isSummary) {
            uiSchemaParser.getSummary(
              mgoResourceJson = mgoResource.json,
              organizationName = organization.name,
            )
          } else {
            uiSchemaParser.getDetails(
              mgoResourceJson = mgoResource.json,
              organizationName = organization.name,
            )
          }
        val uiSchemaSections = uiSchemaSectionMapper.map(uiSchema)
        _viewState.update { viewState ->
          viewState.copy(toolbarTitle = uiSchema.label, sections = uiSchemaSections)
        }
      }
    }

    fun onClickReferenceRow(row: UISchemaRow.Reference) {
      viewModelScope.launch {
        val mgoResource = mgoResourceStore.get(row.referenceId)
        _navigate.tryEmit(mgoResource.referenceId)
      }
    }

    fun onClickFileRow(row: UISchemaRow.Binary.NotDownloaded) {
      viewModelScope.launch(ioDispatcher) {
        // This organization should have a document resource endpoint to get the binary from
        val endpoint = organization.getDocumentsResourceEndpoint() ?: return@launch

        // Set loading state
        val loadingRow = UISchemaRow.Binary.Loading(heading = row.heading, value = row.value)
        updateRow(loadingRow)

        // Download file
        fhirRepository
          .fetchBinary(resourceEndpoint = endpoint, url = "$dvaApiBaseUrl/fhir/${row.binary}")
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
                  if (oldRow.heading == newRow.heading) {
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
