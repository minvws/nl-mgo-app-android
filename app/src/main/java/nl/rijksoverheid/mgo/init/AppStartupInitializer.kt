package nl.rijksoverheid.mgo.init

import android.content.Context
import androidx.startup.Initializer
import dagger.hilt.android.EarlyEntryPoints
import kotlinx.coroutines.runBlocking

class AppStartupInitializer : Initializer<Unit> {
  override fun create(context: Context) {
    val appContext = context.applicationContext
    val entryPoint =
      EarlyEntryPoints.get(
        appContext,
        AppStartupInitializerEntryPoint::class.java,
      )

    // Initialize feature toggles
    runBlocking {
      entryPoint.featureToggleLocalDataSource().init(entryPoint.featureToggleRepository().getAll())
      entryPoint.jsRuntimeRepository().load()
      entryPoint.cacheFileStore().deleteAll()
    }
  }

  override fun dependencies(): List<Class<out Initializer<*>?>?> = listOf()
}
