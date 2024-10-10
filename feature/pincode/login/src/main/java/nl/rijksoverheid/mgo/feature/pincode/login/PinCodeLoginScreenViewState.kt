package nl.rijksoverheid.mgo.feature.pincode.login

import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.framework.copy.R

internal data class PinCodeLoginScreenViewState(
    val pinCode: List<Int>,
    @StringRes val subHeading: Int,
    val error: Boolean,
) {
    companion object {
        val initialState =
            PinCodeLoginScreenViewState(
                pinCode = listOf(),
                subHeading = R.string.pincode_confirm_subheading,
                error = false,
            )
    }
}
