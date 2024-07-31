package nl.rijksoverheid.mgo.modules

import android.content.Context
import com.scottyab.rootbeer.RootBeer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import nl.nl.rijksoverheid.mgo.framework.network.auth.MgoAuthentication
import nl.rijksoverheid.mgo.BuildConfig
import nl.rijksoverheid.mgo.devicerooted.ShowDeviceRootedDialog
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
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
    fun provideShowDeviceRootedDialog(
        @ApplicationContext context: Context,
        keyValueStore: KeyValueStore,
    ): ShowDeviceRootedDialog {
        val rootBeer = RootBeer(context)
        return ShowDeviceRootedDialog(rootBeer = rootBeer, keyValueStore = keyValueStore)
    }

    @Named("appFlavor")
    @Provides
    fun provideAppFlavor(): String {
        return BuildConfig.FLAVOR
    }

    @Named("versionCode")
    @Provides
    fun provideVersionCode(): Int {
        return BuildConfig.VERSION_CODE
    }

    @Provides
    fun provideMgoAuthentication(): MgoAuthentication {
        val basicAuthUser = BuildConfig.BASIC_AUTH_USER
        val basicAuthPassword = BuildConfig.BASIC_AUTH_PASSWORD
        if (basicAuthUser.isNotEmpty() && basicAuthPassword.isNotEmpty()) {
            return MgoAuthentication.Basic(user = basicAuthUser, password = basicAuthPassword)
        }
        return MgoAuthentication.None
    }
}
