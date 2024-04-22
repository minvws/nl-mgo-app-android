package nl.rijksoverheid.mgo.feature.localisation.search

import androidx.annotation.StringRes

data class SearchScreenInput(
    val input: String,
    @StringRes val error: Int?,
)
