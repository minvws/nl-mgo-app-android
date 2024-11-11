package nl.rijksoverheid.mgo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import nl.rijksoverheid.mgo.data.healthcare.ObserveHealthCareDataStates
import timber.log.Timber
import timber.log.Timber.Forest.plant
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var observeHealthCareDataStates: ObserveHealthCareDataStates

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }
        coroutineScope.launch {
            launch { observeHealthCareDataStates.invoke().collect() }
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onLowMemory() {
        super.onLowMemory()
        coroutineScope.cancel()
    }
}
