package nl.rijksoverheid.mgo.feature.organization.problems

import nl.rijksoverheid.mgo.data.concern.models.MgoConcern

data class ProblemsScreenViewState(
    val loading: Boolean,
    val concerns: List<MgoConcern>,
    val error: Throwable?,
) {
    companion object {
        val initialState =
            ProblemsScreenViewState(
                loading = true,
                concerns = listOf(),
                error = null,
            )
    }
}
