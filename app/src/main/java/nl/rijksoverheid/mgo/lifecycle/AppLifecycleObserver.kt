package nl.rijksoverheid.mgo.lifecycle

import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlinx.coroutines.flow.MutableSharedFlow

/**
 * Custom DefaultLifecycleObserver that can keep track if the app is coming from the background, or going to the background.
 * @param appLifecycleState Flow that will emit [AppLifecycleState.FromBackground] is app is coming from background,
 * or [AppLifecycleState.ToBackground] if going to background.
 */
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

sealed class AppLifecycleState {
  data object FromBackground : AppLifecycleState()

  data object ToBackground : AppLifecycleState()
}
