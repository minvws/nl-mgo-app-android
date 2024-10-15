package nl.rijksoverheid.mgo.feature.pincode.login

import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.framework.copy.R

internal data class PinCodeLoginScreenViewState(
    @StringRes val subHeading: Int,
    val error: Boolean,
) {
    companion object {
        val initialState =
            PinCodeLoginScreenViewState(
                subHeading = R.string.pincode_confirm_subheading,
                error = false,
            )
    }
}
