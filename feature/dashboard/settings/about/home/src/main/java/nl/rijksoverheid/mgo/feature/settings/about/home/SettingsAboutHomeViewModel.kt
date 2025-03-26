package nl.rijksoverheid.mgo.feature.settings.about.home

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.fhirParser.version.GetFhirParserVersion
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

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
    ) : ViewModel() {
        private val _viewState =
            MutableStateFlow(
                SettingsAboutHomeScreenViewState(
                    appVersionCode = versionCode,
                    appVersionName = versionName,
                    fhirParserVersion = getFhirParserVersion(),
                ),
            )
        val viewState = _viewState.asStateFlow()
    }
