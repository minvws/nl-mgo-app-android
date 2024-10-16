package nl.rijksoverheid.mgo.feature.pincode.confirm

import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

internal data class PinCodeConfirmScreenViewState(
    @StringRes val subHeading: Int,
    val error: Boolean,
) {
    companion object {
        val initialState =
            PinCodeConfirmScreenViewState(
                subHeading = CopyR.string.pincode_confirm_subheading,
                error = false,
            )
    }
}
