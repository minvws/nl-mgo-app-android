package nl.rijksoverheid.mgo

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.onboarding.HasSeenOnboarding
import javax.inject.Inject

@HiltViewModel
class MainViewModel
    @Inject
    constructor(private val hasSeenOnboarding: HasSeenOnboarding) : ViewModel() {
        fun hasSeenOnboarding(): Boolean {
            return hasSeenOnboarding.invoke()
        }
    }
