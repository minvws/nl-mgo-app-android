package nl.rijksoverheid.mgo.feature.pincode.confirm

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
