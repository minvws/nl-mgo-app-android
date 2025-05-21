package nl.rijksoverheid.mgo.feature.localisation.addOrganization

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject
import nl.rijksoverheid.mgo.framework.copy.R as CopyR

/**
 * The [ViewModel] for [AddOrganizationScreen].
 */
@HiltViewModel
class AddOrganizationScreenViewModel
  @Inject
  constructor() : ViewModel() {
    private val _viewState = MutableStateFlow(AddOrganizationScreenViewState.initialState)
    val viewState = _viewState.stateIn(viewModelScope, SharingStarted.Lazily, AddOrganizationScreenViewState.initialState)

    private val _navigation = MutableSharedFlow<Unit>(extraBufferCapacity = 1)
    val navigation = _navigation.asSharedFlow()

    /**
     * Set the name of the health care provider to search for.
     *
     * @param name The name of the health care provider.
     */
    fun setName(name: String) {
      _viewState.update { viewState -> viewState.copy(name = name) }
    }

    /**
     * Set the city of the health care provider to search for.
     *
     * @param city The city of the health care provider.
     */
    fun setCity(city: String) {
      _viewState.update { viewState -> viewState.copy(city = city) }
    }

    /**
     * Validate if the inputted name and city are valid. Will update the UI reflecting if the input is valid or not.
     */
    fun validate() {
      viewModelScope.launch {
        val name = _viewState.value.name
        val city = _viewState.value.city
        val nameError = if (name.isEmpty()) CopyR.string.add_organization_error_missing_name else null
        val cityError = if (city.isEmpty()) CopyR.string.add_organization_error_missing_city else null
        _viewState.update { viewState -> viewState.copy(nameError = nameError, cityError = cityError) }
        if (nameError == null && cityError == null) {
          _navigation.tryEmit(Unit)
        }
      }
    }
  }
