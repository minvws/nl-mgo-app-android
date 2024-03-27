package nl.rijksoverheid.mgo

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.onboarding.HasSeenOnboarding
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

@HiltViewModel
class MainActivityViewModel
    @Inject
    constructor(
        private val hasSeenOnboarding: HasSeenOnboarding,
    ) : ViewModel() {
        fun hasSeenOnboarding(): Boolean {
            return runBlocking { hasSeenOnboarding.invoke() }
        }
    }
