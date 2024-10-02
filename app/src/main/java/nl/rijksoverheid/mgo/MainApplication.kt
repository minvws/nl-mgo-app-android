package nl.rijksoverheid.mgo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import nl.rijksoverheid.mgo.component.snackbar.SnackBarRepository
import nl.rijksoverheid.mgo.data.healthcare.ObserveHealthCareDataStates
import timber.log.Timber
import timber.log.Timber.Forest.plant
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var observeHealthCareDataStates: ObserveHealthCareDataStates

    @Inject
    lateinit var snackBarRepository: SnackBarRepository

    private val coroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Main)

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            plant(Timber.DebugTree())
        }
        coroutineScope.launch {
            launch { observeHealthCareDataStates.invoke().collect() }

            // When ever a Snackbar should be shown, dismiss it here so it never displays twice
            launch {
                snackBarRepository.get().collect {
                    delay(100)
                    snackBarRepository.dismiss()
                }
            }
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        coroutineScope.cancel()
    }
}
