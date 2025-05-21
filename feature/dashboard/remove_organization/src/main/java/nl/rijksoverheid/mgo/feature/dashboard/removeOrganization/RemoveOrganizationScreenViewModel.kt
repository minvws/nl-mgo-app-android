package nl.rijksoverheid.mgo.feature.dashboard.removeOrganization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.component.mgo.snackbar.DefaultLocalDashboardSnackbarPresenter
import nl.rijksoverheid.mgo.component.mgo.snackbar.MgoSnackBarType
import nl.rijksoverheid.mgo.component.mgo.snackbar.MgoSnackBarVisuals
import nl.rijksoverheid.mgo.data.localisation.OrganizationRepository
import javax.inject.Inject
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * The [ViewModel] for the [RemoveOrganizationScreen].
 *
 * @param organizationRepository The [OrganizationRepository] to get the health care provider from an id.
 */
@HiltViewModel
class RemoveOrganizationScreenViewModel
  @Inject
  constructor(
    private val organizationRepository: OrganizationRepository,
  ) : ViewModel() {
    private val _providerDeleted = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val providerDeleted = _providerDeleted.asSharedFlow()

    /**
     * Delete the health care provider. Also alerts the UI to show a snackbar explaining that the provider has been deleted.
     *
     * @param snackbarPresenter The [DefaultLocalDashboardSnackbarPresenter] to communicate to the UI that a snackbar should be shown.
     * @param organizationId The health care provider id.
     */
    fun delete(
      snackbarPresenter: DefaultLocalDashboardSnackbarPresenter,
      organizationId: String,
    ) {
      viewModelScope.launch {
        val organizationToDelete = organizationRepository.get().first { organization -> organization.id == organizationId }
        snackbarPresenter.showSnackbar(
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
