package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.component.error.GetErrorBanner
import nl.rijksoverheid.mgo.component.fhir.GetRequests
import nl.rijksoverheid.mgo.component.organization.MgoOrganization
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.healthCategories.FavoriteHealthCategoriesRepository
import nl.rijksoverheid.mgo.data.healthCategories.GetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryId
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Named

@HiltViewModel(assistedFactory = HealthCategoriesScreenViewModel.Factory::class)
internal class HealthCategoriesScreenViewModel
  @AssistedInject
  constructor(
    @Assisted("filterOrganization") private val filterOrganization: MgoOrganization? = null,
    private val fhirRepository: FhirRepository,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
    favoriteRepository: FavoriteHealthCategoriesRepository,
    private val organizationRepository: OrganizationRepository,
    private val getRequests: GetRequests,
    private val getHealthCategoriesFromDisk: GetHealthCategoriesFromDisk,
    getErrorBanner: GetErrorBanner,
    @Named("keyValueStore") keyValueStore: KeyValueStore,
  ) : ViewModel() {
    @AssistedFactory
    interface Factory {
      fun create(
        @Assisted("filterOrganization") filterOrganization: MgoOrganization?,
      ): HealthCategoriesScreenViewModel
    }

    private val groups = getHealthCategoriesFromDisk()
    private val initialFavorites = runBlocking(ioDispatcher) { favoriteRepository.observe().firstOrNull() ?: listOf() }
    private val initialViewState =
      HealthCategoriesScreenViewState.initialState(
        providers = runBlocking { organizationRepository.get() },
        automaticLocalisationEnabled = keyValueStore.getBoolean(KEY_AUTOMATIC_LOCALISATION),
        favorites = groups.getFavorites(initialFavorites),
        groups = groups.filterFavorites(initialFavorites),
      )
    private val _viewState = MutableStateFlow(initialViewState)
    private val organizationsFlow =
      if (filterOrganization ==
        null
      ) {
        organizationRepository.storedOrganizationsFlow
      } else {
        flow { emit(listOf(filterOrganization)) }
      }
    private val errorBannerFlow =
      organizationsFlow.flatMapLatest { organizations ->
        getErrorBanner(
          organizations = organizations,
          categories = getHealthCategoriesFromDisk.invoke().map { group -> group.categories }.flatten(),
        )
      }
    val viewState =
      combine(
        _viewState,
        organizationRepository.storedOrganizationsFlow,
        favoriteRepository.observe(),
        errorBannerFlow,
      ) { viewState, providers, favorites, banner ->
        HealthCategoriesScreenViewState(
          name = viewState.name,
          providers = providers,
          automaticLocalisationEnabled = keyValueStore.getBoolean(KEY_AUTOMATIC_LOCALISATION),
          groups = groups.filterFavorites(favorites),
          favorites = groups.getFavorites(favorites),
          banner = banner,
        )
      }.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)

    private fun List<HealthCategoryGroup>.filterFavorites(favorites: List<HealthCategoryId>): List<HealthCategoryGroup> =
      this.map { group -> group.copy(categories = group.categories.filter { category -> !favorites.contains(category.id) }) }

    private fun List<HealthCategoryGroup>.getFavorites(favorites: List<HealthCategoryId>): List<HealthCategoryGroup.HealthCategory> =
      favorites.mapNotNull { categoryId -> this.map { group -> group.categories }.flatten().firstOrNull { it.id == categoryId } }

    fun retry() {
      viewModelScope.launch(ioDispatcher) {
        // Get categories
        val categories = getHealthCategoriesFromDisk().map { group -> group.categories }.flatten()

        // Get requests
        val organizations = if (filterOrganization == null) organizationRepository.get() else listOf(filterOrganization)
        val requests = getRequests(organizations = organizations, categories = categories)

        // Get responses that failed
        val failedResponses =
          fhirRepository
            .observe()
            .first()
            .filterIsInstance<FhirResponse.Error>()
            .filter { response -> requests.contains(response.request) }

        // Map to requests
        val failedRequests = failedResponses.map { response -> response.request }

        // Retry
        fhirRepository.retry(failedRequests)
      }
    }
  }
