package nl.rijksoverheid.mgo.component.snackbar

import androidx.annotation.VisibleForTesting
import javax.inject.Singleton
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull

@Singleton
@VisibleForTesting
class DefaultSnackBarRepository : SnackBarRepository {
    private var visuals = MutableStateFlow<MgoSnackBarVisuals?>(null)

    override fun show(visuals: MgoSnackBarVisuals) {
        this.visuals.value = visuals
    }

    override fun dismiss() {
        this.visuals.value = null
    }

    override fun get(): Flow<MgoSnackBarVisuals> {
        return visuals.filterNotNull()
    }
}
