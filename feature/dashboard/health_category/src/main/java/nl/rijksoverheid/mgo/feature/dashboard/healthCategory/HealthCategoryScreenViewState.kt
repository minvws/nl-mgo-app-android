package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.getTitle

data class HealthCategoryScreenViewState(
    @StringRes val title: Int,
    val listItemsState: ListItemsState,
) {
    sealed class ListItemsState {
        data object Loading : ListItemsState()

        data class Loaded(val listItems: List<HealthCategoryScreenListItem>) : ListItemsState()

        data object NoData : ListItemsState()
    }

    companion object {
        fun initialState(category: HealthCareCategory): HealthCategoryScreenViewState {
            return HealthCategoryScreenViewState(title = category.getTitle(), listItemsState = ListItemsState.Loading)
        }
    }
}
