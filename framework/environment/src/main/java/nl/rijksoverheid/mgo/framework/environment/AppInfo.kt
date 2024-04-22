package nl.rijksoverheid.mgo.framework.environment

open class AppInfo(open val versionCode: Int, open val appFlavor: AppFlavor)

enum class AppFlavor {
    TEST,
    ACC,
    PROD,
}
