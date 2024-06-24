package nl.rijksoverheid.mgo.framework.environment

open class AppInfo(open val versionCode: Int, open val appFlavor: AppFlavor) {
    fun isProductionBuild(): Boolean {
        return this.appFlavor == AppFlavor.PROD
    }

    fun getPrivacyUrl(): String {
        return when (appFlavor) {
            AppFlavor.TEST -> "https://web.test.mgo.irealisatie.nl/privacy"
            AppFlavor.ACC -> "https://web.test.mgo.irealisatie.nl/privacy"
            AppFlavor.PROD -> "https://web.test.mgo.irealisatie.nl/privacy"
        }
    }
}

enum class AppFlavor {
    TEST,
    ACC,
    PROD,
}
