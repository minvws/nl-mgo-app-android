package nl.rijksoverheid.mgo.feature.pincode.create

import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.framework.copy.R

internal data class PinCodeCreateScreenViewState(
    val pinCode: List<Int>,
    @StringRes val subHeading: Int,
    val error: Boolean,
) {
    companion object {
        val initialState =
            PinCodeCreateScreenViewState(
                pinCode = listOf(),
                subHeading = R.string.pincode_create_subheading,
                error = false,
            )
    }
}
