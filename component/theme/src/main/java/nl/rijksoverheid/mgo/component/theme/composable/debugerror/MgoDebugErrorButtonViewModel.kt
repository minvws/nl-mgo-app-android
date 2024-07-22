package nl.rijksoverheid.mgo.component.theme.composable.debugerror

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import nl.rijksoverheid.mgo.framework.environment.Environment
import nl.rijksoverheid.mgo.framework.environment.EnvironmentRepository
import javax.inject.Inject

@HiltViewModel
internal class MgoDebugErrorButtonViewModel
    @Inject
    constructor(environmentRepository: EnvironmentRepository) : ViewModel() {
        val showButton = environmentRepository.getEnvironment() !is Environment.Prod
    }
