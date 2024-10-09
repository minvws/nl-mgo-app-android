package nl.rijksoverheid.mgo.feature.pincode.create

data class PinCodeCreateScreenViewState(
    val pinCode: List<Int>,
) {
    companion object {
        val initialState =
            PinCodeCreateScreenViewState(
                pinCode = listOf(),
            )
    }
}
