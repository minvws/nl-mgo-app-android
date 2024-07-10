package nl.rijksoverheid.mgo.component.theme.composable.debugerror

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.framework.environment.AppInfo
import javax.inject.Inject

@HiltViewModel
internal class MgoDebugErrorButtonViewModel
    @Inject
    constructor(appInfo: AppInfo) : ViewModel() {
        val showButton = !appInfo.isProductionBuild()
    }
