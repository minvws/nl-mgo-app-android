package nl.rijksoverheid.mgo.feature.healthcareprovider.removeprovider

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.HealthCareProviderRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RemoveProviderScreenViewModel
    @Inject
    constructor(
        private val healthCareProviderRepository: HealthCareProviderRepository,
    ) : ViewModel() {
        private val _providerDeleted = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
        val providerDeleted = _providerDeleted.asSharedFlow()

        fun delete(providerId: String) {
            viewModelScope.launch {
                healthCareProviderRepository.delete(providerId)
                _providerDeleted.tryEmit(Unit)
            }
        }
    }
