package nl.rijksoverheid.mgo.feature.settings.advanced

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.framework.environment.featureToggle.FeatureToggle
import nl.rijksoverheid.mgo.framework.environment.featureToggle.FeatureToggleRepository
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
internal class SettingsAdvancedScreenViewModel
  @Inject
  constructor(
    private val featureToggleRepository: FeatureToggleRepository,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
  ) : ViewModel() {
    private val initialViewState =
      SettingsAdvancedScreenViewState(
        featureToggles = featureToggleRepository.get(),
      )
    private val _viewState =
      featureToggleRepository.observe().map { toggles ->
        SettingsAdvancedScreenViewState(
          featureToggles = toggles,
        )
      }
    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, initialViewState)

    fun setToggle(
      toggle: FeatureToggle<*>,
      value: Boolean,
    ) {
      viewModelScope.launch(ioDispatcher) {
        featureToggleRepository.set(toggle = toggle, value = value)
      }
    }
  }
