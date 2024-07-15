package nl.rijksoverheid.mgo.feature.organization.removeOrganization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

@HiltViewModel
class RemoveOrganizationScreenViewModel
    @Inject
    constructor(
        private val organizationRepository: OrganizationRepository,
    ) : ViewModel() {
        private val _providerDeleted = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
        val providerDeleted = _providerDeleted.asSharedFlow()

        fun delete(providerId: String) {
            viewModelScope.launch {
                organizationRepository.delete(providerId)
                _providerDeleted.tryEmit(Unit)
            }
        }
    }
