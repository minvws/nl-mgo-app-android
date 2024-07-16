package nl.rijksoverheid.mgo.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.BuildConfig
import nl.rijksoverheid.mgo.DefaultAppInfo
import nl.rijksoverheid.mgo.framework.environment.AppFlavor
import nl.rijksoverheid.mgo.framework.environment.AppInfo
import nl.rijksoverheid.mgo.framework.environment.Environment
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
internal object AppInfoModule {
    @Provides
    @Singleton
    fun provideAppInfo(): AppInfo {
        val appFlavor =
            when (BuildConfig.FLAVOR) {
                "tst" -> AppFlavor.TEST
                "acc" -> AppFlavor.ACC
                "prod" -> AppFlavor.PROD
                else -> AppFlavor.TEST
            }
        return DefaultAppInfo(versionCode = BuildConfig.VERSION_CODE, appFlavor = appFlavor)
    }

    @Provides
    @Singleton
    fun provideEnvironment(): Environment {
        return when (BuildConfig.FLAVOR) {
            "tst" -> Environment.Tst
            "acc" -> Environment.Acc
            "prod" -> Environment.Prod
            else -> Environment.Tst
        }
    }
}
