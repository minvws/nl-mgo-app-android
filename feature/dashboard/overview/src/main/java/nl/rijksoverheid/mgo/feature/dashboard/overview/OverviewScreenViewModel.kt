package nl.rijksoverheid.mgo.feature.dashboard.overview

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.medication.MedicationRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@HiltViewModel
internal class OverviewScreenViewModel
    @Inject
    constructor(
        organizationRepository: OrganizationRepository,
        medicationRepository: MedicationRepository,
    ) : ViewModel() {
        private val initialViewState =
            OverviewScreenViewState.initialState(
                providers = runBlocking { organizationRepository.get() },
            )
        private val _viewState = MutableStateFlow(initialViewState)
        val viewState =
            combine(_viewState, organizationRepository.storedOrganizationsFlow) { viewState, providers ->
                OverviewScreenViewState(name = viewState.name, providers = providers)
            }.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)

        init {
            viewModelScope.launch {
                val organizations = organizationRepository.get()
                organizations.forEach { organization ->
                    medicationRepository.getMedications(organizationId = organization.id, resourceEndpoint = organization.resourceEndpoint)
                }
            }
        }
    }
