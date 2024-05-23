package nl.rijksoverheid.mgo.feature.onboarding.privacyoverview

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

@HiltViewModel
internal class PrivacyOverviewScreenViewModel
    @Inject
    constructor(
        private val setHasSeenOnboarding: SetHasSeenOnboarding,
    ) : ViewModel() {
        fun setHasSeenOnboarding() {
            runBlocking { setHasSeenOnboarding.invoke(true) }
        }
    }
