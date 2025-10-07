package nl.rijksoverheid.mgo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.init.AppInitializer
import timber.log.Timber
import timber.log.Timber.Forest.plant
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
  private val applicationScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)

  @Inject
  lateinit var appInitializer: AppInitializer

  @Inject
  lateinit var featureToggleRepository: FeatureToggleRepository

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      plant(Timber.DebugTree())
    }
    applicationScope.launch {
      appInitializer.init()
    }
  }
}
