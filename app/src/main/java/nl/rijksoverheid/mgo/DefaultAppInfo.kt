package nl.rijksoverheid.mgo

import nl.rijksoverheid.mgo.framework.environment.AppFlavor
import nl.rijksoverheid.mgo.framework.environment.AppInfo

data class DefaultAppInfo(override val versionCode: Int, override val appFlavor: AppFlavor) : AppInfo(versionCode, appFlavor)
