package nl.rijksoverheid.mgo.component.pincode

import androidx.annotation.DrawableRes

sealed class KeyboardItemType {
    data class Number(val number: Int) : KeyboardItemType()

    data class Icon(
        @DrawableRes val icon: Int,
    ) : KeyboardItemType()
}
