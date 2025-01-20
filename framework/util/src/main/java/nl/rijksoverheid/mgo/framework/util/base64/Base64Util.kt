package nl.rijksoverheid.mgo.framework.util.base64

interface Base64Util {
    fun encode(str: String): String

    fun decode(base64Str: String): String
}
