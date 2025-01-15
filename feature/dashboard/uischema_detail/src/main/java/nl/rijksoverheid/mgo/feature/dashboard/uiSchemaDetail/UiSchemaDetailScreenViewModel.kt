package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceJson
import nl.rijksoverheid.mgo.data.fhirParser.shared.UIElement
import nl.rijksoverheid.mgo.data.fhirParser.shared.UIElementType
import nl.rijksoverheid.mgo.data.fhirParser.shared.UISchema
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.UiSchemaMapper
import nl.rijksoverheid.mgo.data.healthcare.binary.HealthCareBinaryRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.MgoResourceRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataServiceType
import timber.log.Timber
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = UiSchemaDetailScreenViewModel.Factory::class)
internal class UiSchemaDetailScreenViewModel
    @AssistedInject
    constructor(
        @Assisted val organization: MgoOrganization,
        @Assisted private val mgoResource: MgoResourceJson,
        @Assisted private val isSummary: Boolean,
        private val healthCareBinaryRepository: HealthCareBinaryRepository,
        private val uiSchemaMapper: UiSchemaMapper,
        private val mgoResourceRepository: MgoResourceRepository,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(
                organization: MgoOrganization,
                mgoResource: MgoResourceJson,
                isSummary: Boolean,
            ): UiSchemaDetailScreenViewModel
        }

        private val _uiSchema = MutableStateFlow<UISchema?>(null)
        val uiSchema = _uiSchema.asStateFlow()

        private val _navigate = MutableSharedFlow<MgoResourceJson>(extraBufferCapacity = 1)
        val navigate = _navigate.asSharedFlow()

        private val _attachmentsState = MutableStateFlow<Map<UIElement, AttachmentState>>(mapOf())
        val attachmentsState = _attachmentsState.asStateFlow()

        init {
            viewModelScope.launch {
                val uiSchema =
                    if (isSummary) {
                        uiSchemaMapper.getSummary(mgoResource)
                    } else {
                        uiSchemaMapper.getDetail(mgoResource)
                    }
                _uiSchema.value = uiSchema

                // Set initial attachment states
                _attachmentsState.value =
                    uiSchema.children
                        .map { group -> group.children }
                        .flatten()
                        .filter { entry -> entry.type == UIElementType.DownloadLink }
                        .associateWith {
                            AttachmentState.NotDownloaded
                        }
            }
        }

        fun getMgoResource(referenceId: String) {
            viewModelScope.launch {
                mgoResourceRepository.get(referenceId)
                    .onSuccess { mgoResource ->
                        _navigate.tryEmit(mgoResource)
                    }
                    .onFailure { error ->
                        Timber.e(error, "Failed to get mgo resource")
                    }
            }
        }

        fun onDownloadAttachment(entry: UIElement) {
            viewModelScope.launch {
                val resourceEndpoint =
                    organization.dataServices.firstOrNull { service ->
                        service.type ==
                            MgoOrganizationDataServiceType
                                .DOCUMENTS
                    }?.resourceEndpoint ?: return@launch
                val entryUrl = entry.url ?: return@launch

                // Set loading state
                updateAttachmentState(uiEntry = entry, state = AttachmentState.Loading)

                // Download attachment
                healthCareBinaryRepository
                    .download(resourceEndpoint = resourceEndpoint, fhirBinary = entryUrl)
                    .onSuccess { binary ->
                        if (entry.url.isNullOrEmpty()) {
                            updateAttachmentState(
                                uiEntry = entry,
                                state = AttachmentState.Empty,
                            )
                        } else {
                            updateAttachmentState(
                                uiEntry = entry,
                                state = AttachmentState.Downloaded(binary),
                            )
                        }
                    }
                    .onFailure { error ->
                        Timber.e(error, "Could not download attachment")
                        updateAttachmentState(
                            uiEntry = entry,
                            state = AttachmentState.Error(error),
                        )
                    }
            }
        }

        private fun updateAttachmentState(
            uiEntry: UIElement,
            state: AttachmentState,
        ) {
            _attachmentsState.update { states ->
                states.toMutableMap().also { it.put(uiEntry, state) }
            }
        }
    }
