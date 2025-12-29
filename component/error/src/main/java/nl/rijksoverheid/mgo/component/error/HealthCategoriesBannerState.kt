package nl.rijksoverheid.mgo.component.error

sealed class HealthCategoriesBannerState {
  data object Loading : HealthCategoriesBannerState()

  sealed class Error(
    open val partial: Boolean,
  ) : HealthCategoriesBannerState() {
    data class UserError(
      override val partial: Boolean,
    ) : Error(partial)

    data class ServerError(
      override val partial: Boolean,
    ) : Error(partial)
  }
}
