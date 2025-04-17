package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import nl.rijksoverheid.mgo.data.healthcare.healthCareDataStates.CollectHealthCareDataStates
import javax.inject.Inject
import javax.inject.Named

/**
 * The [ViewModel] for [DashboardBottomBarScreen].
 * Since the [DashboardBottomBarScreen] is the root screen of our app, we want to fetch the health care data
 * when this screen is shown. That is what this ViewModel is responsible for.
 *
 * @param collectHealthCareDataStates The [CollectHealthCareDataStates] to initialize getting the health care data.
 * @param ioDispatcher The [CoroutineDispatcher] to indicate on which coroutine [CollectHealthCareDataStates] is executed.
 */
@HiltViewModel
internal class DashboardBottomBarScreenViewModel
  @Inject
  constructor(
    private val collectHealthCareDataStates: CollectHealthCareDataStates,
    @Named("ioDispatcher") private val ioDispatcher: CoroutineDispatcher,
  ) : ViewModel() {
    init {
      viewModelScope.launch(ioDispatcher) {
        collectHealthCareDataStates.invoke().collect()
      }
    }
  }
