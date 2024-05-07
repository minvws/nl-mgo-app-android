package nl.rijksoverheid.mgo.feature.localisation.search

import androidx.annotation.StringRes

data class HealthCareSearchViewState(
    val name: String,
    @StringRes val nameError: Int?,
    val city: String,
    @StringRes val cityError: Int?,
) {
    companion object {
        val initialState =
            HealthCareSearchViewState(
                name = "",
                nameError = null,
                city = "",
                cityError = null,
            )
    }
}
