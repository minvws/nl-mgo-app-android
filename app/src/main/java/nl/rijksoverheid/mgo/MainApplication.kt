package nl.rijksoverheid.mgo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import timber.log.Timber
import timber.log.Timber.Forest.plant
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
  @Inject
  lateinit var appInitializer: AppInitializer

  private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      plant(Timber.DebugTree())
    }
    appInitializer(coroutineScope)
  }

  @Deprecated("Deprecated in Java")
  override fun onLowMemory() {
    super.onLowMemory()
    coroutineScope.cancel()
  }
}
