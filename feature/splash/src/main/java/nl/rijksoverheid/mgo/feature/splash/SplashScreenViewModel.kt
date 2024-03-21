package nl.rijksoverheid.mgo.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.config.ConfigRepository
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class SplashScreenViewModel
    @Inject
    constructor(private val configRepository: ConfigRepository) : ViewModel() {
        private val _navigation = MutableSharedFlow<NavigationScreen>(extraBufferCapacity = 1)
        val navigation = _navigation.asSharedFlow()

        init {
            viewModelScope.launch {
                configRepository
                    .getConfig()
                    .onSuccess {
                        _navigation.tryEmit(NavigationScreen.Onboarding.Start)
                    }
                    .onFailure {
                        // TODO Error state
                    }
            }
        }
    }
