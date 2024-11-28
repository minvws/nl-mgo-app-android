package nl.rijksoverheid.mgo.feature.dashboard.uiSchemaDetail

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import nl.rijksoverheid.mgo.data.uiSchema.UIEntry
import nl.rijksoverheid.mgo.data.uiSchema.UIEntryType
import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import java.io.File
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = UiSchemaDetailScreenViewModel.Factory::class)
internal class UiSchemaDetailScreenViewModel
    @AssistedInject
    constructor(
        @Assisted private val uiSchema: UISchema,
        @ApplicationContext private val context: Context,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(uiSchema: UISchema): UiSchemaDetailScreenViewModel
        }

        private val _attachmentStates = MutableStateFlow<List<AttachmentState>>(listOf())
        val attachmentStates = _attachmentStates.asStateFlow()

        init {
            // Loop through all UI entries, and set initial state for all attachments that can be downloaded
            _attachmentStates.tryEmit(
                uiSchema.children
                    .map { group -> group.children }
                    .flatten()
                    .filter { entry -> entry.type == UIEntryType.DownloadLink }
                    .mapNotNull { entry ->
                        val entryUrl = entry.url ?: return@mapNotNull null
                        AttachmentState.NotDownloaded(label = entry.label, url = entryUrl)
                    },
            )
        }

        fun onDownloadAttachment(entry: UIEntry) {
            viewModelScope.launch {
                _attachmentStates.update { states ->
                    states.map { state ->
                        if (state.label == entry.label) {
                            AttachmentState.Loading(entry.label)
                        } else {
                            state
                        }
                    }
                }

                delay(3000)

                _attachmentStates.update { states ->
                    states.map { state ->
                        if (state.label == entry.label) {
                            AttachmentState.Downloaded(
                                label = entry.label,
                                file = File(context.cacheDir, "example.pdf"),
                                contentType = "application/pdf",
                            )
                        } else {
                            state
                        }
                    }
                }
            }
        }

        override fun onCleared() {
            super.onCleared()

            // Clean up all downloaded attachments
            _attachmentStates.value
                .filterIsInstance<AttachmentState.Downloaded>()
                .forEach { state ->
                    check(state.file.delete()) { "Could not delete file" }
                }
        }
    }
