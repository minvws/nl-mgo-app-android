package nl.rijksoverheid.mgo.feature.pincode.login

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
