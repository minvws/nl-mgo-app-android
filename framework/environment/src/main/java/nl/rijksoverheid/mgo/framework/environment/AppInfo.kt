package nl.rijksoverheid.mgo.framework.environment

import nl.rijksoverheid.mgo.framework.environment.AppFlavor.PROD

open class AppInfo(open val versionCode: Int, open val appFlavor: AppFlavor) {
    fun isProductionBuild(): Boolean {
        return this.appFlavor == PROD
    }
}

enum class AppFlavor {
    TEST,
    ACC,
    PROD,
}
