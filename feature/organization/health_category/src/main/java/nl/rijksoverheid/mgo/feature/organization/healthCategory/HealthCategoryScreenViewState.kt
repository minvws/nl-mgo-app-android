package nl.rijksoverheid.mgo.feature.organization.healthCategory

data class HealthCategoryScreenViewState(
    val listItems: List<HealthCategoryScreenListItem>,
) {
    companion object {
        val initialState = HealthCategoryScreenViewState(listItems = listOf())
    }
}
