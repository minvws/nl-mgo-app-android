package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.data.fhir.FhirRepository
import nl.rijksoverheid.mgo.data.fhir.FhirRequest
import nl.rijksoverheid.mgo.data.fhir.FhirResponse
import nl.rijksoverheid.mgo.data.healthCategories.FavoriteHealthCategoriesRepository
import nl.rijksoverheid.mgo.data.healthCategories.GetHealthCategoriesFromDisk
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryGroup
import nl.rijksoverheid.mgo.data.healthCategories.models.HealthCategoryId
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.feature.dashboard.healthCategories.banner.GetHealthCategoriesBanner
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
internal class HealthCategoriesScreenViewModel
  @Inject
  constructor(
    private val fhirRepository: FhirRepository,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
    @Named("dvaApiBaseUrl") private val dvaApiBaseUrl: String,
    favoriteRepository: FavoriteHealthCategoriesRepository,
    organizationRepository: OrganizationRepository,
    getHealthCategoriesFromDisk: GetHealthCategoriesFromDisk,
    getHealthCategoriesBanner: GetHealthCategoriesBanner,
    @Named("keyValueStore") keyValueStore: KeyValueStore,
  ) : ViewModel() {
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
    val viewState =
      combine(
        _viewState,
        organizationRepository.storedOrganizationsFlow,
        favoriteRepository.observe(),
        getHealthCategoriesBanner.invoke(),
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

    /**
     * Retry FHIR requests.
     *
     * @param failedOnly If true, only retries the failed fhir requests.
     */
    fun retry(failedOnly: Boolean) {
      viewModelScope.launch(ioDispatcher) {
        val fhirResponses =
          if (failedOnly) {
            fhirRepository.observe().first().filterIsInstance<FhirResponse.Error>()
          } else {
            fhirRepository.observe().first()
          }

        fhirRepository.deleteFailed()
        retry(fhirResponses)
      }
    }

    private suspend fun retry(fhirResponses: List<FhirResponse>) {
      for (fhirResponse in fhirResponses) {
        val request =
          FhirRequest(
            organizationId = fhirResponse.request.organizationId,
            medmijId = fhirResponse.request.medmijId,
            dataServiceId = fhirResponse.request.dataServiceId,
            endpointId = fhirResponse.request.endpointId,
            endpointPath = fhirResponse.request.endpointPath,
            resourceEndpoint = fhirResponse.request.resourceEndpoint,
            fhirVersion = fhirResponse.request.fhirVersion,
            url = "$dvaApiBaseUrl/fhir${fhirResponse.request.endpointPath}",
          )

        fhirRepository.fetch(
          request = request,
          forceRefresh = true,
        )
      }
    }
  }
