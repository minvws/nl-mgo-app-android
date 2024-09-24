package nl.rijksoverheid.mgo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import nl.rijksoverheid.mgo.data.healthcare.HealthCareStateRepository
import timber.log.Timber
import timber.log.Timber.Forest.plant
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var healthCareStateRepository: HealthCareStateRepository

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }
        loadHealthCareData()
    }

    private fun loadHealthCareData() {
        coroutineScope.launch {
            healthCareStateRepository.init()
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        coroutineScope.cancel()
    }
}
