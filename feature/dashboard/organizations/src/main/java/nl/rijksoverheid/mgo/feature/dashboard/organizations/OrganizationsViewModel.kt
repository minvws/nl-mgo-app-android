package nl.rijksoverheid.mgo.feature.dashboard.organizations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking

@HiltViewModel
internal class OrganizationsViewModel
    @Inject
    constructor(
        organizationRepository: OrganizationRepository,
    ) : ViewModel() {
        private val initialViewState =
            OrganizationsViewState.initialState(
                organizations = runBlocking { organizationRepository.get() },
            )
        private val _viewState = MutableStateFlow(initialViewState)
        val viewState =
            combine(_viewState, organizationRepository.storedOrganizationsFlow) { _, organizations ->
                OrganizationsViewState(organizations = organizations)
            }.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)
    }
