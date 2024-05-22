package nl.rijksoverheid.mgo.feature.localisation.search

import androidx.annotation.StringRes

data class SearchScreenViewState(
    val name: String,
    @StringRes val nameError: Int?,
    val city: String,
    @StringRes val cityError: Int?,
) {
    companion object {
        val initialState =
            SearchScreenViewState(
                name = "",
                nameError = null,
                city = "",
                cityError = null,
            )
    }
}
