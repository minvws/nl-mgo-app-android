package nl.rijksoverheid.mgo.feature.pincode.confirm

/**
 * The view state for [PinCodeConfirmScreen].
 *
 * @param error True if the pin codes do not match.
 */
internal data class PinCodeConfirmScreenViewState(
  val error: Boolean,
) {
  companion object {
    val initialState =
      PinCodeConfirmScreenViewState(
        error = false,
      )
  }
}
