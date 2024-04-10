package nl.rijksoverheid.mgo.feature.addhealthcare.search

import androidx.annotation.StringRes

data class SearchScreenInput(
    val input: String,
    @StringRes val error: Int?,
)
