package nl.rijksoverheid.mgo.feature.organization.medicationUse

data class MedicationUseScreenViewState(
    val listItems: List<MedicationUseScreenListItem>,
) {
    companion object {
        val initialState = MedicationUseScreenViewState(listItems = listOf())
    }
}
