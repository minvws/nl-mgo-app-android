package nl.rijksoverheid.mgo.feature.pincode.create

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
