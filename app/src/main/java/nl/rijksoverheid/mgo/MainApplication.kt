package nl.rijksoverheid.mgo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.data.healthcare.binary.HealthCareBinaryRepository
import nl.rijksoverheid.mgo.framework.featuretoggle.dataSource.FeatureToggleLocalDataSource
import nl.rijksoverheid.mgo.framework.featuretoggle.repository.FeatureToggleRepository
import timber.log.Timber
import timber.log.Timber.Forest.plant
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var featureToggleRepository: FeatureToggleRepository

    @Inject
    lateinit var featureToggleLocalDataSource: FeatureToggleLocalDataSource

    @Inject
    lateinit var healthCareBinaryRepository: HealthCareBinaryRepository

    @Inject
    lateinit var jsRuntimeRepository: JsRuntimeRepository

    @Inject
    @Named("ioDispatcher")
    lateinit var ioDispatcher: CoroutineDispatcher

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }

        // Initialize feature toggles
        runBlocking { featureToggleLocalDataSource.init(featureToggleRepository.getAll()) }

        coroutineScope.launch(ioDispatcher) {
            jsRuntimeRepository.load()

            // Check if we need to clean up cached attachments
            launch { healthCareBinaryRepository.cleanup() }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onLowMemory() {
        super.onLowMemory()
        coroutineScope.cancel()
    }
}
