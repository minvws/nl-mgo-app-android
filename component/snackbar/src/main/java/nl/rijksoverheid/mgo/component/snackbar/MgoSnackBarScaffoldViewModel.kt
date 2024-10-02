package nl.rijksoverheid.mgo.component.snackbar

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
internal class MgoSnackBarScaffoldViewModel
    @Inject
    constructor(
        snackBarRepository: SnackBarRepository,
    ) : ViewModel() {
        val visuals = snackBarRepository.get()
    }
