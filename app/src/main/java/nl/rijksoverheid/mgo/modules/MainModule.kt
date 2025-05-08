package nl.rijksoverheid.mgo.modules

import android.content.Context
import com.scottyab.rootbeer.RootBeer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import nl.nl.rijksoverheid.mgo.framework.network.auth.MgoAuthentication
import nl.rijksoverheid.mgo.BuildConfig
import nl.rijksoverheid.mgo.devicerooted.ShowDeviceRootedDialog
import nl.rijksoverheid.mgo.framework.storage.keyvalue.KeyValueStore
import nl.rijksoverheid.mgo.lock.AppLocked
import nl.rijksoverheid.mgo.lock.DefaultAppLocked
import nl.rijksoverheid.mgo.lock.DefaultSaveClosedAppTimestamp
import nl.rijksoverheid.mgo.lock.SaveClosedAppTimestamp
import java.io.File
import java.time.Clock
import javax.inject.Named
import javax.inject.Singleton

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
  fun provideClock(): Clock {
    return Clock.systemUTC()
  }

  @Provides
  @Singleton
  fun provideShowDeviceRootedDialog(
    @ApplicationContext context: Context,
    @Named("keyValueStore") keyValueStore: KeyValueStore,
  ): ShowDeviceRootedDialog {
    val rootBeer = RootBeer(context)
    return ShowDeviceRootedDialog(rootBeer = rootBeer, keyValueStore = keyValueStore)
  }

  @Named("appFlavor")
  @Provides
  fun provideAppFlavor(): String {
    return BuildConfig.FLAVOR
  }

  @Named("isDebug")
  @Provides
  fun provideIsDebug(): Boolean {
    return BuildConfig.DEBUG
  }

  @Named("versionName")
  @Provides
  fun provideVersionName(): String {
    return BuildConfig.VERSION_NAME
  }

  @Named("versionCode")
  @Provides
  fun provideVersionCode(): Int {
    return BuildConfig.VERSION_CODE
  }

  @Named("deeplinkHost")
  @Provides
  fun provideDeeplinkHost(): String {
    return BuildConfig.DEEPLINK_HOST
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

  @Provides
  fun provideAppLocked(
    clock: Clock,
    @Named("keyValueStore") keyValueStore: KeyValueStore,
    @Named("secureKeyValueStore") secureKeyValueStore: KeyValueStore,
  ): AppLocked {
    return DefaultAppLocked(clock = clock, keyValueStore = keyValueStore, secureKeyValueStore = secureKeyValueStore)
  }

  @Provides
  fun provideSaveClosedAppTimestamp(
    clock: Clock,
    @Named("keyValueStore") keyValueStore: KeyValueStore,
  ): SaveClosedAppTimestamp {
    return DefaultSaveClosedAppTimestamp(clock = clock, keyValueStore)
  }

  @Provides
  @Named("ioDispatcher")
  fun provideIoDispatcher(): CoroutineDispatcher {
    return Dispatchers.IO
  }
}
