package nl.rijksoverheid.mgo.feature.settings.about.accessibility

import androidx.annotation.StringRes
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import javax.inject.Inject
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@HiltViewModel
internal class SettingsAboutAccessibilityScreenViewModel
  @Inject
  constructor(
    private val environmentRepository: EnvironmentRepository,
  ) : ViewModel() {
    @StringRes
    fun getUrl(): Int {
      return when (environmentRepository.getEnvironment()) {
        is Environment.Acc -> CopyR.string.settings_accessibility_more_information_url_acc
        is Environment.Custom -> CopyR.string.settings_accessibility_more_information_url_test
        is Environment.Demo -> CopyR.string.settings_accessibility_more_information_url_acc
        is Environment.Prod -> CopyR.string.settings_accessibility_more_information_url_prod
        is Environment.Tst -> CopyR.string.settings_accessibility_more_information_url_test
      }
    }
  }
