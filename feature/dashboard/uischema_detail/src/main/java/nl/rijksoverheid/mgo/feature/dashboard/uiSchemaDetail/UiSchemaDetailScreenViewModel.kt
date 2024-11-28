package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.healthcare.binary.HealthCareBinaryRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganizationDataServiceType
import nl.rijksoverheid.mgo.data.uiSchema.UIEntry
import nl.rijksoverheid.mgo.data.uiSchema.UIEntryType
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import timber.log.Timber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = UiSchemaDetailScreenViewModel.Factory::class)
internal class UiSchemaDetailScreenViewModel
    @AssistedInject
    constructor(
        @Assisted private val organization: MgoOrganization,
        @Assisted private val uiSchema: UISchema,
        private val healthCareBinaryRepository: HealthCareBinaryRepository,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(
                organization: MgoOrganization,
                uiSchema: UISchema,
            ): UiSchemaDetailScreenViewModel
        }

        private val _attachmentStates = MutableStateFlow<List<AttachmentState>>(listOf())
        val attachmentStates = _attachmentStates.asStateFlow()

        init {
            // Set initial attachment states
            _attachmentStates.value =
                uiSchema.children
                    .map { group -> group.children }
                    .flatten()
                    .filter { entry -> entry.type == UIEntryType.DownloadLink }
                    .mapNotNull { entry ->
                        val entryUrl = entry.url ?: return@mapNotNull null
                        AttachmentState.NotDownloaded(label = entry.label, url = entryUrl)
                    }
        }

        fun onDownloadAttachment(entry: UIEntry) {
            viewModelScope.launch {
                val resourceEndpoint =
                    organization.dataServices.firstOrNull { service ->
                        service.type ==
                            MgoOrganizationDataServiceType
                                .DOCUMENTS
                    }?.resourceEndpoint ?: return@launch
                val entryUrl = entry.url ?: return@launch

                // Set loading state
                updateAttachmentState(label = entry.label, updatedState = AttachmentState.Loading(label = entry.label))

                // Download attachment
                healthCareBinaryRepository
                    .download(resourceEndpoint = resourceEndpoint, fhirBinary = entryUrl)
                    .onSuccess { binary ->
                        updateAttachmentState(
                            label = entry.label,
                            updatedState =
                                AttachmentState.Downloaded(
                                    label = entry.label,
                                    file = binary.file,
                                    contentType = binary.contentType,
                                ),
                        )
                    }
                    .onFailure { error ->
                        Timber.e(error, "Could not download attachment")
                        updateAttachmentState(
                            label = entry.label,
                            updatedState = AttachmentState.NotDownloaded(label = entry.label, url = entryUrl),
                        )
                    }
            }
        }

        private fun updateAttachmentState(
            label: String,
            updatedState: AttachmentState,
        ) {
            _attachmentStates.update { states ->
                states.map { state ->
                    if (state.label == label) {
                        updatedState
                    } else {
                        state
                    }
                }
            }
        }
    }
