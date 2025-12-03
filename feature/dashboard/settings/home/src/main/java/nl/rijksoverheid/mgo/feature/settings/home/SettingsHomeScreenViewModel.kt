package nl.rijksoverheid.mgo.feature.settings.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import nl.rijksoverheid.mgo.data.pincode.biometric.DeviceHasBiometric
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
internal class SettingsHomeScreenViewModel
  @Inject
  constructor(
    @Named("isDebug") isDebug: Boolean,
    deviceHasBiometric: DeviceHasBiometric,
  ) : ViewModel() {
    private val initialViewState =
      SettingsHomeScreenViewState(
        deviceHasBiometric = deviceHasBiometric.invoke(),
        isDebug = isDebug,
      )
    private val _viewState =
      MutableStateFlow(SettingsHomeScreenViewState(isDebug = isDebug, deviceHasBiometric = deviceHasBiometric.invoke()))

    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)
  }
