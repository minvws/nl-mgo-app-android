package nl.rijksoverheid.mgo

import nl.rijksoverheid.mgo.framework.environment.AppInfo

data object DefaultAppInfo : AppInfo {
    override val versionCode: Int
        get() = BuildConfig.VERSION_CODE
}
