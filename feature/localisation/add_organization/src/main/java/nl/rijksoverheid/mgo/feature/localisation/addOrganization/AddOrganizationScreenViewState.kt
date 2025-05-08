package nl.rijksoverheid.mgo.feature.localisation.addOrganization

import androidx.annotation.StringRes

/**
 * The view state for [AddOrganizationScreen].
 *
 * @param name The name of the health care provider.
 * @param nameError If not null, the error string resource to show indicating that there is a error validating the name.
 * @param city The city of the health care provider.
 * @param cityError If not null, the error string resource to show indicating that there is a error validating the city.
 */
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
