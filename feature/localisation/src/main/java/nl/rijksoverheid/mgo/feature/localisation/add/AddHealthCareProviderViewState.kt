package nl.rijksoverheid.mgo.feature.localisation.add

import nl.rijksoverheid.mgo.data.localisation.models.HealthCareProvider

data class AddHealthCareProviderViewState(
    val providers: List<HealthCareProvider>,
) {
    companion object {
        val initialState = AddHealthCareProviderViewState(listOf())
    }
}
