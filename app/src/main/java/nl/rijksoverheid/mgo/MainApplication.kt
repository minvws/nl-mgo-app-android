package nl.rijksoverheid.mgo

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import nl.rijksoverheid.mgo.data.healthcare.HealthCareRepository
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import timber.log.Timber
import timber.log.Timber.Forest.plant
import javax.inject.Inject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@HiltAndroidApp
class MainApplication : Application() {
    @Inject
    lateinit var healthCareRepository: HealthCareRepository

    @Inject lateinit var organizationRepository: OrganizationRepository

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
            organizationRepository.storedOrganizationsFlow.collectLatest { organizations ->
                for (organization in organizations) {
                    withContext(Dispatchers.IO) {
                        healthCareRepository.getMedications(organization)
                    }
                }
            }
        }
    }

    override fun onLowMemory() {
        super.onLowMemory()
        coroutineScope.cancel()
    }
}
