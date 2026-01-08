package nl.rijksoverheid.mgo.component.error

sealed class ErrorBannerState {
  data object Loading : ErrorBannerState()

  sealed class Error(
    open val partial: Boolean,
  ) : ErrorBannerState() {
    data class UserError(
      override val partial: Boolean,
    ) : Error(partial)

    data class ServerError(
      override val partial: Boolean,
    ) : Error(partial)
  }
}
