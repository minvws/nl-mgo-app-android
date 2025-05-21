package nl.rijksoverheid.mgo.feature.dashboard.organizations

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_AUTOMATIC_LOCALISATION
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import javax.inject.Inject
import javax.inject.Named

/**
 * The [ViewModel] for [OrganizationsScreen].
 *
 * @param organizationRepository The [OrganizationRepository] to get the stored health care providers from.
 * @param keyValueStore The [KeyValueStore] in where is stored if automatic localisation has been enabled.
 */
@HiltViewModel
internal class OrganizationsViewModel
  @Inject
  constructor(
    organizationRepository: OrganizationRepository,
    @Named("keyValueStore") keyValueStore: KeyValueStore,
  ) : ViewModel() {
    private val initialViewState =
      OrganizationsViewState.initialState(
        organizations = runBlocking { organizationRepository.get() },
        automaticLocalisationEnabled = keyValueStore.getBoolean(KEY_AUTOMATIC_LOCALISATION),
      )
    private val _viewState = MutableStateFlow(initialViewState)
    val viewState =
      combine(_viewState, organizationRepository.storedOrganizationsFlow) { _, organizations ->
        OrganizationsViewState(
          organizations = organizations,
          automaticLocalisationEnabled = keyValueStore.getBoolean(KEY_AUTOMATIC_LOCALISATION),
        )
      }.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)
  }
