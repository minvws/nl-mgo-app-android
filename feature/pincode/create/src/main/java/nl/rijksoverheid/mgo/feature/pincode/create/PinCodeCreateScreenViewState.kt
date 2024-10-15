package nl.rijksoverheid.mgo.feature.pincode.create

import androidx.annotation.StringRes
import nl.rijksoverheid.mgo.framework.copy.R

internal data class PinCodeCreateScreenViewState(
    @StringRes val subHeading: Int,
    val error: Boolean,
) {
    companion object {
        val initialState =
            PinCodeCreateScreenViewState(
                subHeading = R.string.pincode_create_subheading,
                error = false,
            )
    }
}
