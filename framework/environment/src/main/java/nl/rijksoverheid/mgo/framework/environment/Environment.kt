package nl.rijksoverheid.mgo.framework.environment

sealed class Environment(open val versionCode: Int) {
    data class Tst(override val versionCode: Int) : Environment(versionCode)

    data class Acc(override val versionCode: Int) : Environment(versionCode)

    data class Prod(override val versionCode: Int) : Environment(versionCode)

    data class Custom(override val versionCode: Int, val url: String) : Environment(versionCode)
}
