package nl.rijksoverheid.mgo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.init.AppInitializer
import timber.log.Timber
import timber.log.Timber.Forest.plant
import javax.inject.Inject

@HiltAndroidApp
class MainApplication : Application() {
  @Inject
  lateinit var appInitializer: AppInitializer

  @Inject
  lateinit var featureToggleRepository: FeatureToggleRepository

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      plant(Timber.DebugTree())
    }
    runBlocking {
      appInitializer.init()
    }
  }
}
