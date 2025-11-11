package nl.rijksoverheid.mgo.feature.settings.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import nl.rijksoverheid.mgo.data.pincode.biometric.DeviceHasBiometric
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import nl.rijksoverheid.mgo.framework.storage.keyvalue.MgoKeyValueStorage
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
internal class SettingsHomeScreenViewModel
  @Inject
  constructor(
    @Named("keyValueStore") private val keyValueStore: KeyValueStore,
    @Named("secureKeyValueStore") private val secureKeyValueStore: KeyValueStore,
    @Named("sharedPreferencesMgoKeyValueStorage") private val keyValueStorage: MgoKeyValueStorage,
    @Named("isDebug") isDebug: Boolean,
    private val organizationRepository: OrganizationRepository,
    deviceHasBiometric: DeviceHasBiometric,
  ) : ViewModel() {
    private val _navigateToOnboarding = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigateToOnboarding = _navigateToOnboarding.asSharedFlow()

    private val initialViewState =
      SettingsHomeScreenViewState(
        deviceHasBiometric = deviceHasBiometric.invoke(),
        isDebug = isDebug,
      )
    private val _viewState =
      MutableStateFlow(SettingsHomeScreenViewState(isDebug = isDebug, deviceHasBiometric = deviceHasBiometric.invoke()))

    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)

    fun resetApp() {
      viewModelScope.launch {
        keyValueStore.clear()
        secureKeyValueStore.clear()
        organizationRepository.deleteAll()
        keyValueStorage.deleteAll()
        _navigateToOnboarding.tryEmit(Unit)
      }
    }
  }
