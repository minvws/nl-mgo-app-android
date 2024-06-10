package nl.rijksoverheid.mgo.feature.healthcareprovider.concern

import nl.rijksoverheid.mgo.data.concern.models.MgoConcern

sealed class ConcernScreenViewState {
    data object Loading : ConcernScreenViewState()

    data class Success(val concerns: List<MgoConcern>) : ConcernScreenViewState()

    data class Error(val isProductionBuild: Boolean, val error: Throwable) : ConcernScreenViewState()
}
