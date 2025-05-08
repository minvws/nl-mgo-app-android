package nl.rijksoverheid.mgo.feature.pincode.create

/**
 * The view state for [PinCodeCreateScreen].
 *
 * @param error True if the pin code is not validated.
 */
internal data class PinCodeCreateScreenViewState(
  val error: Boolean,
) {
  companion object {
    val initialState =
      PinCodeCreateScreenViewState(
        error = false,
      )
  }
}
