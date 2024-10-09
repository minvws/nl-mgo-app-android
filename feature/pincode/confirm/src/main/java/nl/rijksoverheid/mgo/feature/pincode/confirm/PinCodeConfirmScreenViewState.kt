package nl.rijksoverheid.mgo.feature.pincode.confirm

internal data class PinCodeConfirmScreenViewState(
    val pinCode: List<Int>,
    val error: Boolean,
) {
    companion object {
        val initialState =
            PinCodeConfirmScreenViewState(
                pinCode = listOf(),
                error = false,
            )
    }
}
