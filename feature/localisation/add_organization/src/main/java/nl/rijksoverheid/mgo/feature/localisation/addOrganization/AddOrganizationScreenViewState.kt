package nl.rijksoverheid.mgo.feature.localisation.addOrganization

import androidx.annotation.StringRes

data class AddOrganizationScreenViewState(
    val name: String,
    @StringRes val nameError: Int?,
    val city: String,
    @StringRes val cityError: Int?,
) {
    companion object {
        val initialState =
            AddOrganizationScreenViewState(
                name = "",
                nameError = null,
                city = "",
                cityError = null,
            )
    }
}
