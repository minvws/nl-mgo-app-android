package nl.rijksoverheid.mgo.feature.pincode.login

/**
 * The view state for [PinCodeLoginScreen].
 *
 * @param hasBiometric True if the device supports biometric login.
 * @param error True if error needs to be shown.
 */
internal data class PinCodeLoginScreenViewState(
  val hasBiometric: Boolean,
  val error: Boolean,
) {
  companion object {
    fun initialState(hasBiometric: Boolean) =
      PinCodeLoginScreenViewState(
        hasBiometric = hasBiometric,
        error = false,
      )
  }
}
