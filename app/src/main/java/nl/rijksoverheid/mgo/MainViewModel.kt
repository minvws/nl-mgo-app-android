package nl.rijksoverheid.mgo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.config.ConfigRepository
import nl.rijksoverheid.mgo.data.config.ConfigState
import nl.rijksoverheid.mgo.data.onboarding.HasSeenOnboarding
import nl.rijksoverheid.mgo.data.pincode.HasPinCode
import nl.rijksoverheid.mgo.devicerooted.ShowDeviceRootedDialog
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KEY_APP_CLOSED_TIMESTAMP
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import nl.rijksoverheid.mgo.navigation.dashboard.DashboardNavigation
import nl.rijksoverheid.mgo.navigation.onboarding.OnboardingNavigation
import nl.rijksoverheid.mgo.navigation.pincode.PinCodeNavigation
import java.time.Instant
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@HiltViewModel
internal class MainViewModel
    @Inject
    constructor(
        val showDeviceRootedDialog: ShowDeviceRootedDialog,
        @Named("keyValueStore") private val keyValueStore: KeyValueStore,
        private val hasPinCode: HasPinCode,
        private val hasSeenOnboarding: HasSeenOnboarding,
        private val configRepository: ConfigRepository,
    ) : ViewModel() {
        val lockAppFlow = MutableSharedFlow<Boolean>(extraBufferCapacity = 1)

        val configStateFlow =
            configRepository.configStateFlow.stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5000),
                initialValue = ConfigState.NoAction,
            )

        fun getStartDestination(): Any {
            return when {
                hasPinCode.invoke() -> {
                    DashboardNavigation.Root
                }

                hasSeenOnboarding.invoke() -> {
                    PinCodeNavigation.Root
                }

                else -> {
                    OnboardingNavigation.Root
                }
            }
        }

        fun refreshConfig() {
            viewModelScope.launch {
                configRepository.refresh()
            }
        }

        fun getClosedAppTimestamp() {
            viewModelScope.launch {
                val currentTimestamp = Instant.now().epochSecond
                val closedAppTimestamp = keyValueStore.getLong(KEY_APP_CLOSED_TIMESTAMP)
                if (closedAppTimestamp != null) {
                    val closedAppSeconds = currentTimestamp - closedAppTimestamp
                    if (closedAppSeconds > 5L) {
                        lockAppFlow.tryEmit(true)
                    }
                }
            }
        }

        fun saveClosedAppTimestamp() {
            viewModelScope.launch {
                val currentTimestamp = Instant.now().epochSecond
                keyValueStore.setLong(KEY_APP_CLOSED_TIMESTAMP, currentTimestamp)
            }
        }
    }
