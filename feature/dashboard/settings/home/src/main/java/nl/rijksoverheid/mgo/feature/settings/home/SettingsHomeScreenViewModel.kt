package nl.rijksoverheid.mgo.feature.settings.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
internal class SettingsHomeScreenViewModel
  @Inject
  constructor(
    @Named("isDebug") isDebug: Boolean,
  ) : ViewModel() {
    private val initialViewState =
      SettingsHomeScreenViewState(
        isDebug = isDebug,
      )
    private val _viewState =
      MutableStateFlow(SettingsHomeScreenViewState(isDebug = isDebug))

    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)
  }
