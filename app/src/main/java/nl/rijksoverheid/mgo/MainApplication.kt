package nl.rijksoverheid.mgo

import android.app.Application
import androidx.lifecycle.ProcessLifecycleOwner
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.data.healthcare.binary.FhirBinaryRepository
import nl.rijksoverheid.mgo.framework.featuretoggle.dataSource.FeatureToggleLocalDataSource
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import nl.rijksoverheid.mgo.lifecycle.AppLifecycleObserver
import nl.rijksoverheid.mgo.lifecycle.AppLifecycleState
import timber.log.Timber
import timber.log.Timber.Forest.plant
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidApp
class MainApplication : Application() {
  @Inject
  lateinit var featureToggleRepository: FeatureToggleRepository

  @Inject
  lateinit var featureToggleLocalDataSource: FeatureToggleLocalDataSource

  @Inject
  lateinit var fhirBinaryRepository: FhirBinaryRepository

  @Inject
  lateinit var jsRuntimeRepository: JsRuntimeRepository

  @Inject
  @Named("ioDispatcher")
  lateinit var ioDispatcher: CoroutineDispatcher

  val appLifecycleState = MutableSharedFlow<AppLifecycleState>(extraBufferCapacity = 1)
  private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

  override fun onCreate() {
    super.onCreate()
    if (BuildConfig.DEBUG) {
      plant(Timber.DebugTree())
    }

    ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver(appLifecycleState))

    // Initialize feature toggles
    runBlocking { featureToggleLocalDataSource.init(featureToggleRepository.getAll()) }

    coroutineScope.launch(ioDispatcher) {
      jsRuntimeRepository.load()

      // Remove any left over downloaded files on each app launch
      launch { fhirBinaryRepository.cleanup() }
    }
  }

  @Deprecated("Deprecated in Java")
  override fun onLowMemory() {
    super.onLowMemory()
    coroutineScope.cancel()
  }
}
