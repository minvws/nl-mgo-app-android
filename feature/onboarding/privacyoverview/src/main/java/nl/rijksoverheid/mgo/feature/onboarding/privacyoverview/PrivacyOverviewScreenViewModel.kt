package nl.rijksoverheid.mgo.feature.onboarding.privacyoverview

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import nl.rijksoverheid.mgo.framework.environment.AppInfo
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

@HiltViewModel
internal class PrivacyOverviewScreenViewModel
    @Inject
    constructor(
        private val appInfo: AppInfo,
        private val setHasSeenOnboarding: SetHasSeenOnboarding,
    ) : ViewModel() {
        fun getUrl(): String {
            return appInfo.getPrivacyUrl()
        }

        fun setHasSeenOnboarding() {
            runBlocking { setHasSeenOnboarding.invoke(true) }
        }
    }
