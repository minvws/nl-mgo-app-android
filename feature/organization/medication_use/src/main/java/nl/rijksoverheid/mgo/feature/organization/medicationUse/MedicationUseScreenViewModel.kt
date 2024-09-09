package nl.rijksoverheid.mgo.feature.organization.medicationUse

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.uiSchema.store.UiSchemaCacheCategory
import nl.rijksoverheid.mgo.data.uiSchema.store.UiSchemaCacheKey
import nl.rijksoverheid.mgo.data.uiSchema.store.UiSchemaRepository
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
                val organizationIds = organizations.map { organization -> organization.id }
                val uiSchemaList =
                    organizationIds
                        .mapNotNull { providerId ->
                            uiSchemaRepository.get(
                                UiSchemaCacheKey(
                                    organizationId = providerId,
                                    category =
                                        UiSchemaCacheCategory.MEDICATION_USE,
                                ),
                            )
                        }
                        .flatten()
                _viewState.update { viewState -> viewState.copy(uiSchemaList = uiSchemaList) }
            }
        }
    }
