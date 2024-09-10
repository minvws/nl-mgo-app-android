package nl.rijksoverheid.mgo.feature.organization.medicationUse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.uiSchema.repository.UiSchemaCacheCategory
import nl.rijksoverheid.mgo.data.uiSchema.repository.UiSchemaCacheKey
import nl.rijksoverheid.mgo.data.uiSchema.repository.UiSchemaRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel
class MedicationUseScreenViewModel
    @Inject
    constructor(
        private val organizationRepository: OrganizationRepository,
        private val uiSchemaRepository: UiSchemaRepository,
    ) : ViewModel() {
        private val initialState = MedicationUseScreenViewState.initialState
        private val _viewState: MutableStateFlow<MedicationUseScreenViewState> = MutableStateFlow(initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

        init {
            viewModelScope.launch {
                val organizations = organizationRepository.get()
                val listItems =
                    organizations
                        .mapNotNull { organization ->
                            // Get UI Schema
                            uiSchemaRepository.get(
                                UiSchemaCacheKey(
                                    organizationId = organization.id,
                                    category = UiSchemaCacheCategory.MEDICATION_USE,
                                ),
                            )?.map { uiSchema ->
                                // Map UI Schema to List Item
                                MedicationUseScreenListItem(
                                    title = uiSchema.label ?: "",
                                    subtitle = organization.name,
                                    uiSchema = uiSchema,
                                )
                            }
                        }
                        .flatten()
                _viewState.update { viewState -> viewState.copy(listItems = listItems) }
            }
        }
    }
