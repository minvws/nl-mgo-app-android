package nl.rijksoverheid.mgo.feature.pincode.confirm

import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

internal data class PinCodeConfirmScreenViewState(
    val pinCode: List<Int>,
    @StringRes val subHeading: Int,
    val error: Boolean,
) {
    companion object {
        val initialState =
            PinCodeConfirmScreenViewState(
                pinCode = listOf(),
                subHeading = CopyR.string.pincode_confirm_subheading,
                error = false,
            )
    }
}
