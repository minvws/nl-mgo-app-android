package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.UiSchemaMapper
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataState.HealthCareDataState
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.HealthCareDataStatesRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.MgoResourceRepository
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.getProfiles
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.localisation.models.MgoOrganization
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
        private val mgoResourceRepository: MgoResourceRepository,
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
        ): List<HealthCategoryScreenListItemsGroup> {
            return if (this is HealthCareDataState.Loaded) {
                // Get all the mgo resources as one big list
                val mgoResources =
                    this.results
                        .mapNotNull { result -> result.getOrNull() }
                        .flatten()

                // Filter them to only display the onces we want to show
                val filteredMgoResources = mgoResourceRepository.filter(resources = mgoResources, profiles = category.getProfiles())

                // Group them by category
                val groupedMgoResources =
                    filteredMgoResources
                        .groupBy { mgoResource -> mgoResource.getGroupHeading() }

                // Map it to own list items group class
                return groupedMgoResources.toListItemsGroup(uiSchemaMapper = uiSchemaMapper, organization = organization)
            } else {
                listOf()
            }
        }
    }
