package nl.rijksoverheid.mgo.feature.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.config.ConfigRepository
import nl.rijksoverheid.mgo.data.onboarding.HasSeenOnboarding
import nl.rijksoverheid.mgo.framework.navigation.NavigationScreen
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
internal class SplashScreenViewModel
    @Inject
    constructor(private val configRepository: ConfigRepository, private val hasSeenOnboarding: HasSeenOnboarding) : ViewModel() {
        private val _navigation = MutableSharedFlow<NavigationScreen>(extraBufferCapacity = 1)
        val navigation = _navigation.asSharedFlow()

        init {
            getConfig()
        }

        fun getConfig() {
            viewModelScope.launch {
                configRepository
                    .getConfig()
                    .onSuccess {
                        if (hasSeenOnboarding.invoke()) {
                            _navigation.tryEmit(NavigationScreen.Dashboard)
                        } else {
                            _navigation.tryEmit(NavigationScreen.Onboarding.Start)
                        }
                    }
                    .onFailure {
                        // TODO Handle more error cases
                        _navigation.tryEmit(NavigationScreen.Error.NoInternet)
                    }
            }
        }
    }
