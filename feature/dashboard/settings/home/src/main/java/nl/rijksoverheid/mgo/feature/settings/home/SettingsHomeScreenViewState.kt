package nl.rijksoverheid.mgo.feature.settings.home

import nl.rijksoverheid.mgo.component.theme.theme.AppTheme

data class SettingsHomeScreenViewState(
    val appTheme: AppTheme,
    val deviceHasBiometric: Boolean,
)
