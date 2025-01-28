package nl.rijksoverheid.mgo.framework.environment

import javax.inject.Inject
import javax.inject.Named

internal class DefaultEnvironmentRepository
    @Inject
    constructor(
        @Named("appFlavor") private val appFlavor: String,
        @Named("versionCode") private val versionCode: Int,
        @Named("deeplinkHost") private val deeplinkHost: String,
    ) : EnvironmentRepository {
        override fun getEnvironment(): Environment {
            return when (appFlavor) {
                "demo" -> Environment.Demo(versionCode, deeplinkHost)
                "tst" -> Environment.Tst(versionCode, deeplinkHost)
                "acc" -> Environment.Acc(versionCode, deeplinkHost)
                "prod" -> Environment.Prod(versionCode, deeplinkHost)
                else -> Environment.Tst(versionCode, deeplinkHost)
            }
        }
    }
