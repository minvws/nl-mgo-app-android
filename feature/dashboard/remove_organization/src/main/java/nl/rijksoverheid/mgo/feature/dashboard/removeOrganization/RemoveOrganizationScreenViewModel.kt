package nl.rijksoverheid.mgo.feature.dashboard.removeOrganization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.component.snackbar.MgoSnackBarType
import nl.rijksoverheid.mgo.component.snackbar.MgoSnackBarVisuals
import nl.rijksoverheid.mgo.component.snackbar.SnackBarRepository
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import javax.inject.Inject
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

@HiltViewModel
class RemoveOrganizationScreenViewModel
    @Inject
    constructor(
        private val organizationRepository: OrganizationRepository,
        private val snackBarRepository: SnackBarRepository,
    ) : ViewModel() {
        private val _providerDeleted = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
        val providerDeleted = _providerDeleted.asSharedFlow()

        fun delete(organizationId: String) {
            viewModelScope.launch {
                val organizationToDelete = organizationRepository.get().first { organization -> organization.id == organizationId }
                snackBarRepository.show(
                    visuals =
                        MgoSnackBarVisuals(
                            type = MgoSnackBarType.SUCCESS,
                            title = CopyR.string.toast_organization_removed_heading,
                            action = CopyR.string.toast_organization_removed_subheading,
                            actionCallback = {
                                organizationRepository.save(organizationToDelete)
                            },
                        ),
                )
                organizationRepository.delete(organizationId)
                _providerDeleted.tryEmit(Unit)
            }
        }
    }
