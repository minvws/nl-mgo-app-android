package nl.rijksoverheid.mgo.framework.util

import android.annotation.SuppressLint
import android.content.Context
import androidx.annotation.StringRes

@SuppressLint("DiscouragedApi")
@StringRes
fun Context.getStringResourceByName(aString: String): Int {
    return resources.getIdentifier(aString, "string", packageName)
}
