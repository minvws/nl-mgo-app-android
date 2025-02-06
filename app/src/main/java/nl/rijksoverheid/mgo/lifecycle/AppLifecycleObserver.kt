package nl.rijksoverheid.mgo.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableSharedFlow

class AppLifecycleObserver(
    private val appLifecycleState: MutableSharedFlow<AppLifecycleState>,
) : DefaultLifecycleObserver {
    private var isInBackground = false

    override fun onStart(owner: LifecycleOwner) {
        if (isInBackground) {
            isInBackground = false
            appLifecycleState.tryEmit(AppLifecycleState.FromBackground)
        }
    }

    override fun onStop(owner: LifecycleOwner) {
        isInBackground = true
        appLifecycleState.tryEmit(AppLifecycleState.ToBackground)
    }
}
