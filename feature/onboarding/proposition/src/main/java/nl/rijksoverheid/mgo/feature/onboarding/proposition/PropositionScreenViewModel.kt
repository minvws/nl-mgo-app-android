package nl.rijksoverheid.mgo.feature.onboarding.proposition

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import javax.inject.Inject
import kotlinx.coroutines.runBlocking

@HiltViewModel
internal class PropositionScreenViewModel
    @Inject
    constructor(
        private val environmentRepository: EnvironmentRepository,
        private val setHasSeenOnboarding: SetHasSeenOnboarding,
    ) : ViewModel() {
        fun getUrl(): String {
            return environmentRepository.getEnvironment().getPrivacyUrl()
        }

        fun setHasSeenOnboarding() {
            runBlocking { setHasSeenOnboarding.invoke(true) }
        }

        private fun Environment.getPrivacyUrl(): String {
            return when (this) {
                is Environment.Tst -> "https://web.test.mgo.irealisatie.nl/privacy"
                is Environment.Acc -> "https://web.test.mgo.irealisatie.nl/privacy"
                is Environment.Prod -> "https://web.test.mgo.irealisatie.nl/privacy"
                is Environment.Custom -> "https://web.test.mgo.irealisatie.nl/privacy"
            }
        }
    }
