package nl.rijksoverheid.mgo.feature.dashboard.healthCategory

data class HealthCategoryScreenViewState(
    val listItems: List<HealthCategoryScreenListItem>,
) {
    companion object {
        val initialState = HealthCategoryScreenViewState(listItems = listOf())
    }
}
