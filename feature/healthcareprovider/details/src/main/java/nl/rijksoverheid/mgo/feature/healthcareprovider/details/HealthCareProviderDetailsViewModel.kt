package nl.rijksoverheid.mgo.feature.healthcareprovider.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.healthcareprovider.DvaRepository
import timber.log.Timber
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
internal class HealthCareProviderDetailsViewModel
    @Inject
    constructor(private val dvaRepository: DvaRepository) : ViewModel() {
        init {
            viewModelScope.launch {
                dvaRepository
                    .getMedicationStatement()
                    .onSuccess { medicationStatements ->
                        Timber.v("Got medication statement")
                    }
                    .onFailure {
                        Timber.e(it, "Failed to get medication statement")
                    }
            }
        }
    }
