package nl.rijksoverheid.mgo.modules

import android.content.Context
import android.os.Build
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
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
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
  ): File = context.cacheDir

  @Provides
  @Singleton
  @Named("systemUTC")
  fun provideClockUTC(): Clock = Clock.systemUTC()

  @Provides
  @Singleton
  @Named("systemDefaultZone")
  fun provideClockDefaultZone(): Clock = Clock.systemDefaultZone()

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
  fun provideAppFlavor(): String = BuildConfig.FLAVOR

  @Named("isDebug")
  @Provides
  fun provideIsDebug(): Boolean = BuildConfig.DEBUG

  @Named("versionName")
  @Provides
  fun provideVersionName(): String = BuildConfig.VERSION_NAME

  @Named("versionCode")
  @Provides
  fun provideVersionCode(): Int = BuildConfig.VERSION_CODE

  @Named("deeplinkHost")
  @Provides
  fun provideDeeplinkHost(): String = BuildConfig.DEEPLINK_HOST

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
    @Named("systemUTC") clock: Clock,
    @Named("keyValueStore") keyValueStore: KeyValueStore,
    @Named("secureKeyValueStore") secureKeyValueStore: KeyValueStore,
  ): AppLocked = DefaultAppLocked(clock = clock, keyValueStore = keyValueStore, secureKeyValueStore = secureKeyValueStore)

  @Provides
  fun provideSaveClosedAppTimestamp(
    @Named("systemUTC") clock: Clock,
    @Named("keyValueStore") keyValueStore: KeyValueStore,
  ): SaveClosedAppTimestamp = DefaultSaveClosedAppTimestamp(clock = clock, keyValueStore)

  @Provides
  @Named("ioDispatcher")
  fun provideIoDispatcher(): CoroutineDispatcher = Dispatchers.IO

  @Provides
  @Named("sdkVersion")
  fun provideSdkVersion() = Build.VERSION.SDK_INT

  @Provides
  @Singleton
  @Named("loadApiBaseUrl")
  fun provideLoadApiBaseUrl(environmentRepository: EnvironmentRepository): String =
    when (val environment = environmentRepository.getEnvironment()) {
      is Environment.Acc -> "https://lo-ad.acc.mgo.irealisatie.nl"
      is Environment.Prod -> "https://lo-ad.acc.mgo.irealisatie.nl"
      is Environment.Tst -> "https://lo-ad.test.mgo.irealisatie.nl"
      is Environment.Demo -> "https://lo-ad.acc.mgo.irealisatie.nl"
      is Environment.Custom -> environment.url
    }

  // TODO Set urls for other environments when available.
  @Provides
  @Singleton
  @Named("dvaApiBaseUrl")
  fun provideDvaApiBaseUrl(environmentRepository: EnvironmentRepository): String =
    when (val environment = environmentRepository.getEnvironment()) {
      is Environment.Acc -> "https://dvp-proxy.acc.mgo.irealisatie.nl"
      is Environment.Prod -> "https://dvp-proxy.acc.mgo.irealisatie.nl"
      is Environment.Tst -> "https://dvp-proxy.test.mgo.irealisatie.nl"
      is Environment.Demo -> "https://dvp-proxy.acc.mgo.irealisatie.nl"
      is Environment.Custom -> environment.url
    }

  // TODO Set urls for other environments when available.
  @Provides
  @Singleton
  @Named("pftUrl")
  fun providePftUrl(environmentRepository: EnvironmentRepository): String =
    when (val environment = environmentRepository.getEnvironment()) {
      is Environment.Acc -> "https://app-api.test.mgo.irealisatie.nl/v1/mgo/pft.json"
      is Environment.Prod -> "https://app-api.test.mgo.irealisatie.nl/v1/mgo/pft.json"
      is Environment.Tst -> "https://app-api.test.mgo.irealisatie.nl/v1/mgo/pft.json"
      is Environment.Demo -> "https://app-api.test.mgo.irealisatie.nl/v1/mgo/pft.json"
      is Environment.Custom -> environment.url
    }
}
