package nl.rijksoverheid.mgo.framework.util.base64

import android.util.Base64
import javax.inject.Inject

internal class DefaultBase64Util
    @Inject
    constructor() : Base64Util {
        override fun encode(str: String): String {
            return Base64.encodeToString(str.toByteArray(), Base64.DEFAULT)
        }

        override fun decode(base64Str: String): String {
            return String(Base64.decode(base64Str, Base64.DEFAULT), charset("UTF-8"))
        }
    }
