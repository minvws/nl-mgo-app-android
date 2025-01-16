package nl.rijksoverheid.mgo.feature.dashboard.uiSchema

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.UiSchemaMapper
import nl.rijksoverheid.mgo.data.healthcare.binary.FhirBinaryRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.MgoResourceRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.getDocumentsResourceEndpoint
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.UISchemaRow
import nl.rijksoverheid.mgo.feature.dashboard.uiSchema.models.toSections
import timber.log.Timber
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

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

        init {
            viewModelScope.launch {
                val uiSchema =
                    if (isSummary) {
                        uiSchemaMapper.getSummary(mgoResource)
                    } else {
                        uiSchemaMapper.getDetail(mgoResource)
                    }
                _viewState.update { viewState ->
                    viewState.copy(toolbarTitle = uiSchema.label ?: "", sections = uiSchema.toSections())
                }
            }
        }

        /**
         * When clicking on a reference, get the mgo resource and navigate to the UI Schema screen with that resource.
         * @param row The clicked reference row.
         */
        fun onClickReferenceRow(row: UISchemaRow.Reference) {
            viewModelScope.launch {
                mgoResourceRepository.get(row.referenceId)
                    .onSuccess { mgoResource ->
                        _navigate.tryEmit(mgoResource)
                    }
                    .onFailure { error ->
                        Timber.e(error, "Failed to get mgo resource")
                    }
            }
        }

        /**
         * When clicking on a file, download the binary and update the view state to reflect the state of downloading.
         * @param row The clicked file row.
         */
        fun onClickFileRow(row: UISchemaRow.File.NotDownloaded) {
            viewModelScope.launch {
                // This organization should have a document resource endpoint to get the binary from
                val endpoint = organization.getDocumentsResourceEndpoint() ?: return@launch

                // Set loading state
                val loadingRow = UISchemaRow.File.Loading(heading = row.heading, value = row.value)
                updateRow(loadingRow)

                // Download file
                fhirBinaryRepository
                    .download(resourceEndpoint = endpoint, fhirBinary = row.binary)
                    .onSuccess { binary ->
                        val downloadedRow = UISchemaRow.File.Downloaded(heading = row.heading, value = row.value, binary = binary)
                        updateRow(downloadedRow)
                    }
                    .onFailure { error ->
                        Timber.e(error, "Failed to download binary")
                        val errorRow = UISchemaRow.File.NotDownloaded.Error(heading = row.heading, value = row.value, binary = row.binary)
                        updateRow(errorRow)
                    }
            }
        }

        /**
         * Update a row in the view state. Matches based on the value of the row.
         * @param newRow The new row.
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

        override fun onCleared() {
            super.onCleared()
            // Remove all downloaded files when leaving the screen
            viewModelScope.launch {
                fhirBinaryRepository.cleanup()
            }
        }
    }
