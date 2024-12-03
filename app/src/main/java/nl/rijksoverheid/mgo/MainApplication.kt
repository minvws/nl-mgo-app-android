package nl.rijksoverheid.mgo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import nl.rijksoverheid.mgo.data.healthcare.ObserveHealthCareDataStates
import nl.rijksoverheid.mgo.data.healthcare.binary.HealthCareBinaryRepository
import nl.rijksoverheid.mgo.framework.featuretoggle.dataSource.FeatureToggleLocalDataSource
import timber.log.Timber
import timber.log.Timber.Forest.plant
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var observeHealthCareDataStates: ObserveHealthCareDataStates

    @Inject
    lateinit var featureToggleLocalDataSource: FeatureToggleLocalDataSource

    @Inject
    lateinit var healthCareBinaryRepository: HealthCareBinaryRepository

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }

        // Initialize feature toggles
        runBlocking { featureToggleLocalDataSource.init() }

        coroutineScope.launch {
            // Check if we need to clean up cached attachments
            launch { healthCareBinaryRepository.cleanup() }

            // Start the observer for health care data states
            launch { observeHealthCareDataStates.invoke().collect() }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onLowMemory() {
        super.onLowMemory()
        coroutineScope.cancel()
    }
}
