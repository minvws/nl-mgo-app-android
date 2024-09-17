package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking

@HiltViewModel(assistedFactory = HealthCategoriesScreenViewModel.Factory::class)
internal class HealthCategoriesScreenViewModel
    @AssistedInject
    constructor(
        @Assisted("arguments") private val arguments: HealthCategoriesScreenArguments,
        organizationRepository: OrganizationRepository,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(
                @Assisted("arguments") arguments: HealthCategoriesScreenArguments,
            ): HealthCategoriesScreenViewModel
        }

        private val initialViewState =
            HealthCategoriesScreenViewState.initialState(
                filterOrganization = arguments.filterOrganization,
                providers = runBlocking { organizationRepository.get() },
            )
        private val _viewState = MutableStateFlow(initialViewState)
        val viewState =
            combine(_viewState, organizationRepository.storedOrganizationsFlow) { viewState, providers ->
                HealthCategoriesScreenViewState(
                    name = viewState.name,
                    filterOrganization = arguments.filterOrganization,
                    providers = providers,
                )
            }.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)
    }
