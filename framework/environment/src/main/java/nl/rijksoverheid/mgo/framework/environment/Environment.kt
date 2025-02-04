package nl.rijksoverheid.mgo.framework.environment

sealed class Environment(open val versionCode: Int, open val deeplinkHost: String) {
    data class Demo(override val versionCode: Int, override val deeplinkHost: String) : Environment(versionCode, deeplinkHost)

    data class Tst(override val versionCode: Int, override val deeplinkHost: String) : Environment(versionCode, deeplinkHost)

    data class Acc(override val versionCode: Int, override val deeplinkHost: String) : Environment(versionCode, deeplinkHost)

    data class Prod(override val versionCode: Int, override val deeplinkHost: String) : Environment(versionCode, deeplinkHost)

    data class Custom(override val versionCode: Int, override val deeplinkHost: String, val url: String) :
        Environment(versionCode, deeplinkHost)
}
