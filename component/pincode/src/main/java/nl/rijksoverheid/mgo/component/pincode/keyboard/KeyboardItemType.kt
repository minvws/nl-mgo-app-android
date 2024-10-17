package nl.rijksoverheid.mgo.component.pincode.keyboard

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

sealed class KeyboardItemType {
    data class Number(val number: Int) : KeyboardItemType()

    data class Icon(
        @DrawableRes val icon: Int,
        @StringRes val talkBack: Int,
    ) : KeyboardItemType()
}
