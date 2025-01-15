package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import nl.rijksoverheid.mgo.data.healthcare.mgoResource.HealthCareCategory

data class HealthCategoryScreenViewState(
    val category: HealthCareCategory,
    val showErrorBanner: Boolean,
    val listItemsState: ListItemsState,
) {
    sealed class ListItemsState {
        data object Loading : ListItemsState()

        data class Loaded(val listItems: List<HealthCategoryScreenListItem>) : ListItemsState()

        data object NoData : ListItemsState()
    }

    companion object {
        fun initialState(category: HealthCareCategory): HealthCategoryScreenViewState {
            return HealthCategoryScreenViewState(
                category = category,
                showErrorBanner = false,
                listItemsState = ListItemsState.Loading,
            )
        }
    }
}
