package nl.rijksoverheid.mgo.feature.settings.about.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import nl.rijksoverheid.mgo.data.fhirParser.version.GetFhirParserVersion
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import javax.inject.Inject
import javax.inject.Named
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * The [ViewModel] for [SettingsAboutHomeScreen].
 *
 * @param versionCode The version code of the app.
 * @param versionName The version name of the app.
 * @param getFhirParserVersion The [GetFhirParserVersion] to get the version of the fhir parser being used in the app.
 */
@HiltViewModel
internal class SettingsAboutHomeViewModel
  @Inject
  constructor(
    @Named("versionCode") versionCode: Int,
    @Named("versionName") versionName: String,
    getFhirParserVersion: GetFhirParserVersion,
    environmentRepository: EnvironmentRepository,
  ) : ViewModel() {
    private val _viewState =
      MutableStateFlow(
        SettingsAboutHomeScreenViewState(
          appVersionCode = versionCode,
          appVersionName = versionName,
          fhirParserVersion = getFhirParserVersion(),
          privacyUrl =
            when (environmentRepository.getEnvironment()) {
              is Environment.Acc -> CopyR.string.privacy_link_acc
              is Environment.Custom -> CopyR.string.privacy_link_test
              is Environment.Demo -> CopyR.string.privacy_link_acc
              is Environment.Prod -> CopyR.string.privacy_link_prod
              is Environment.Tst -> CopyR.string.privacy_link_test
            },
        ),
      )
    val viewState = _viewState.asStateFlow()
  }
