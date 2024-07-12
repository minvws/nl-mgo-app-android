package nl.rijksoverheid.mgo.feature.localisation.organizationList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import javax.inject.Inject
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class OrganizationListScreenViewModel
    @Inject
    constructor(
        private val organizationRepository: OrganizationRepository,
    ) : ViewModel() {
        private val _viewState =
            organizationRepository.storedHealthCareProvidersFlow.map { providers ->
                OrganizationListScreenViewState(providers = providers)
            }
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, OrganizationListScreenViewState.initialState)

        fun delete(provider: MgoOrganization) {
            viewModelScope.launch {
                organizationRepository.delete(provider.id)
            }
        }
    }
