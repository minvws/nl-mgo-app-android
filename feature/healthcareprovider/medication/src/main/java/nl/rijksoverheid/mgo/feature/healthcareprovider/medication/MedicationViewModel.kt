package nl.rijksoverheid.mgo.feature.healthcareprovider.medication

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.medication.MedicationRepository
import timber.log.Timber
import javax.inject.Inject
import kotlinx.coroutines.launch

@HiltViewModel
class MedicationViewModel
    @Inject
    constructor(private val medicationRepository: MedicationRepository) : ViewModel() {
        init {
            viewModelScope.launch {
                medicationRepository
                    .getMedications()
                    .onSuccess { medications -> Timber.v("Successfully fetched medications. Amount: ${medications.size}") }
                    .onFailure { error -> Timber.e(error, "Failed to fetch medication") }
            }
        }
    }
