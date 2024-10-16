package nl.rijksoverheid.mgo.feature.pincode.login

import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.framework.copy.R

internal data class PinCodeLoginScreenViewState(
    val hasBiometric: Boolean,
    @StringRes val subHeading: Int,
    val error: Boolean,
) {
    companion object {
        fun initialState(hasBiometric: Boolean) =
            PinCodeLoginScreenViewState(
                hasBiometric = hasBiometric,
                subHeading = R.string.pincode_confirm_subheading,
                error = false,
            )
    }
}
