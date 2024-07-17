package nl.rijksoverheid.mgo.framework.environment

import javax.inject.Named

class DefaultEnvironmentRepository(
    @Named("buildFlavor") private val appFlavor: String,
    @Named("versionCode") private val versionCode: Int,
) : EnvironmentRepository {
    override fun getEnvironment(): Environment {
        return when (appFlavor) {
            "tst" -> Environment.Tst(versionCode)
            "acc" -> Environment.Acc(versionCode)
            "prod" -> Environment.Prod(versionCode)
            else -> Environment.Tst(versionCode)
        }
    }
}
