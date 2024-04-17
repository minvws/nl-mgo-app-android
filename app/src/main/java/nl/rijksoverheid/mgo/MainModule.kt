package nl.rijksoverheid.mgo

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.rijksoverheid.mgo.framework.environment.AppFlavor
import nl.rijksoverheid.mgo.framework.environment.AppInfo
import nl.rijksoverheid.mgo.framework.environment.Environment
import java.io.File
import java.time.Clock
import javax.inject.Named
import javax.inject.Singleton
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@InstallIn(SingletonComponent::class)
@Module
internal object MainModule {
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

    @Provides
    @Singleton
    @Named("cacheDir")
    fun provideCacheDir(
        @ApplicationContext context: Context,
    ): File {
        return context.cacheDir
    }

    @Provides
    @Singleton
    @Named("backgroundDispatcher")
    fun provideBackgroundDispatcher(): CoroutineDispatcher {
        return Dispatchers.IO
    }

    @Provides
    @Singleton
    fun provideClock(): Clock {
        return Clock.systemUTC()
    }

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
}
