package nl.rijksoverheid.mgo.feature.dashboard.bottombar

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.data.healthcare.CollectHealthCareDataStates
import javax.inject.Inject
import javax.inject.Named
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

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
