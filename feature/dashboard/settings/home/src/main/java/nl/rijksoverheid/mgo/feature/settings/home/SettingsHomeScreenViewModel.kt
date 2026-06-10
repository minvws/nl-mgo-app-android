package nl.rijksoverheid.mgo.feature.settings.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import javax.inject.Inject

@HiltViewModel
internal class SettingsHomeScreenViewModel
  @Inject
  constructor(
    val environmentRepository: EnvironmentRepository,
  ) : ViewModel() {
    private val initialViewState =
      SettingsHomeScreenViewState(
        showAdvancedScreen = environmentRepository.getEnvironment() is Environment.Tst,
      )
    private val _viewState =
      MutableStateFlow(initialViewState)

    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)
  }
