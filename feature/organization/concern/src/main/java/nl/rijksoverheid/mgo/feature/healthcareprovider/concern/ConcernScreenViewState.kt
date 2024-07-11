package nl.rijksoverheid.mgo.feature.healthcareprovider.concern

import nl.rijksoverheid.mgo.data.concern.models.MgoConcern

data class ConcernScreenViewState(
    val loading: Boolean,
    val concerns: List<MgoConcern>,
    val error: Throwable?,
) {
    companion object {
        val initialState =
            ConcernScreenViewState(
                loading = true,
                concerns = listOf(),
                error = null,
            )
    }
}
