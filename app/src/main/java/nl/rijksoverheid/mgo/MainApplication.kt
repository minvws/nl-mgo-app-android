package nl.rijksoverheid.mgo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.data.hcimParser.javascript.QuickJsRepository
import nl.rijksoverheid.mgo.data.pft.PftRepository
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.init.AppInitializer
import nl.rijksoverheid.mgo.init.FhirResponseSyncer
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

  @Inject
  lateinit var fhirResponseSyncer: FhirResponseSyncer

  @Inject
  lateinit var pftRepository: PftRepository

  @Inject
  lateinit var quickJsRepository: QuickJsRepository

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      plant(Timber.DebugTree())
    }
    runBlocking { appInitializer.init() }
    applicationScope.launch(Dispatchers.IO) {
      launch { quickJsRepository.create() }
      launch { pftRepository.sync() }
    }
  }
}
