package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.data.healthcare.HealthCareCategory
import nl.rijksoverheid.mgo.data.healthcare.getTitle

data class HealthCategoryScreenViewState(
    @StringRes val title: Int,
    val listItems: List<HealthCategoryScreenListItem>,
) {
    companion object {
        fun initialState(category: HealthCareCategory): HealthCategoryScreenViewState {
            return HealthCategoryScreenViewState(
                title = category.getTitle(),
                listItems = listOf(),
            )
        }
    }
}
