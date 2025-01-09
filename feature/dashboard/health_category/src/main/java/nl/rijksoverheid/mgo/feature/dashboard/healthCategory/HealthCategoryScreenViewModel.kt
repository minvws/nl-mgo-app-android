package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.HealthCareDataStatesRepository
import nl.rijksoverheid.mgo.data.healthcare.getProfiles
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
import nl.rijksoverheid.mgo.data.uiSchema.UiSchemaMapper
import timber.log.Timber
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = HealthCategoryScreenViewModel.Factory::class)
internal class HealthCategoryScreenViewModel
    @AssistedInject
    constructor(
        @Assisted("category") private val category: HealthCareCategory,
        @Assisted("filterOrganization") private val filterOrganization: MgoOrganization? = null,
        private val organizationRepository: OrganizationRepository,
        private val healthCareDataStatesRepository: HealthCareDataStatesRepository,
        private val uiSchemaMapper: UiSchemaMapper,
    ) : ViewModel() {
        @AssistedFactory
        interface Factory {
            fun create(
                @Assisted("category") category: HealthCareCategory,
                @Assisted("filterOrganization") filterOrganization: MgoOrganization? = null,
            ): HealthCategoryScreenViewModel
        }

        private val initialState = HealthCategoryScreenViewState.initialState(category)
        private val _viewState: MutableStateFlow<HealthCategoryScreenViewState> = MutableStateFlow(initialState)
        val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialState)

        init {
            viewModelScope.launch {
                healthCareDataStatesRepository.observe(
                    category = category,
                    filterOrganization = filterOrganization,
                ).distinctUntilChanged()
                    .collectLatest { states ->
                        val loading = states.any { state -> state is HealthCareDataState.Loading }
                        val empty = states.all { state -> state is HealthCareDataState.Empty }
                        val listItems =
                            states.map { state ->
                                state.toListItems(
                                    organization = state.organization,
                                    category = state.category,
                                )
                            }
                                .flatten()
                        val error =
                            states
                                .filterIsInstance<HealthCareDataState.Loaded>()
                                .any { state -> state.results.any { result -> result.isFailure } }

                        _viewState.update {
                            val listItemState =
                                when {
                                    loading -> HealthCategoryScreenViewState.ListItemsState.Loading
                                    empty -> HealthCategoryScreenViewState.ListItemsState.NoData
                                    else -> HealthCategoryScreenViewState.ListItemsState.Loaded(listItems)
                                }
                            HealthCategoryScreenViewState(
                                category = category,
                                showErrorBanner = error,
                                listItemsState = listItemState,
                            )
                        }
                    }
            }
        }

        fun retry() {
            viewModelScope.launch {
                if (filterOrganization == null) {
                    val organizations = organizationRepository.get()
                    for (organization in organizations) {
                        healthCareDataStatesRepository.refresh(category = category, organization = organization)
                    }
                } else {
                    healthCareDataStatesRepository.refresh(category = category, organization = filterOrganization)
                }
            }
        }

        private suspend fun HealthCareDataState.toListItems(
            organization: MgoOrganization,
            category: HealthCareCategory,
        ): List<HealthCategoryScreenListItem> {
            return if (this is HealthCareDataState.Loaded) {
                this.results.map { it.getOrNull() ?: listOf() }.flatten().let { jsons ->
                    uiSchemaMapper.getSummary(resources = jsons, profiles = category.getProfiles())
                }.map { uiSchema ->
                    HealthCategoryScreenListItem(
                        title = uiSchema.label ?: "",
                        subtitle = organization.name,
                        uiSchema = uiSchema,
                        organization = organization,
                    )
                }
            } else {
                listOf()
            }
        }
    }
