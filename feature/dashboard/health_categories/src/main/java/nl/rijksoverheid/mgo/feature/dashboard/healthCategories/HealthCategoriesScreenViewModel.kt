package nl.rijksoverheid.mgo.feature.dashboard.healthCategories

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.data.healthcare.mgoResource.category.HealthCareCategoriesRepository
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

/**
 * The [ViewModel] for [HealthCategoriesScreen].
 * Observes added organizations that is used to show empty state or not.
 *
 * @param organizationRepository The [OrganizationRepository] to fetch the added organizations.
 * @param keyValueStore The [KeyValueStore] to get if the automatic localisation feature is enabled.
 */
@HiltViewModel
internal class HealthCategoriesScreenViewModel
  @Inject
  constructor(
    healthCareCategoriesRepository: HealthCareCategoriesRepository,
    organizationRepository: OrganizationRepository,
    @Named("keyValueStore") keyValueStore: KeyValueStore,
  ) : ViewModel() {
    private val initialViewState =
      HealthCategoriesScreenViewState.initialState(
        providers = runBlocking { organizationRepository.get() },
        automaticLocalisationEnabled = keyValueStore.getBoolean(KEY_AUTOMATIC_LOCALISATION),
        categories = runBlocking { healthCareCategoriesRepository.observe().first() },
      )
    private val _viewState = MutableStateFlow(initialViewState)
    val viewState =
      combine(_viewState, organizationRepository.storedOrganizationsFlow, healthCareCategoriesRepository.observe()) { viewState, providers, categories ->
        HealthCategoriesScreenViewState(
          name = viewState.name,
          providers = providers,
          automaticLocalisationEnabled = keyValueStore.getBoolean(KEY_AUTOMATIC_LOCALISATION),
          categories = categories,
          favorites = categories.filter { category -> category.favoritePosition != -1 }.sortedBy { category -> category.favoritePosition },
        )
      }.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)
  }
