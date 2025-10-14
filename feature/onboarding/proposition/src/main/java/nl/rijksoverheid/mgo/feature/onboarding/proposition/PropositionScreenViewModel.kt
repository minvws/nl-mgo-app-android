package nl.rijksoverheid.mgo.feature.onboarding.proposition

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.onboarding.SetHasSeenOnboarding
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import javax.inject.Inject

@HiltViewModel
internal class PropositionScreenViewModel
  @Inject
  constructor(
    private val environmentRepository: EnvironmentRepository,
    private val setHasSeenOnboarding: SetHasSeenOnboarding,
  ) : ViewModel() {
    /**
     * TODO Add the correct urls for all the environments.
     * Get the privacy url to open in a browser to show the privacy policy.
     * @return The privacy url.
     */
    fun getPrivacyUrl(): String =
      when (environmentRepository.getEnvironment()) {
        is Environment.Tst -> "https://web.test.mgo.irealisatie.nl/privacy"
        is Environment.Demo -> "https://web.test.mgo.irealisatie.nl/privacy"
        is Environment.Acc -> "https://web.test.mgo.irealisatie.nl/privacy"
        is Environment.Prod -> "https://web.test.mgo.irealisatie.nl/privacy"
        is Environment.Custom -> "https://web.test.mgo.irealisatie.nl/privacy"
      }

    /**
     * Set that the onboarding has been seen.
     */
    fun setHasSeenOnboarding() {
      setHasSeenOnboarding.invoke(true)
    }
  }
