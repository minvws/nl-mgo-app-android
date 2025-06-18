package nl.rijksoverheid.mgo.lifecycle

import androidx.lifecycle.ProcessLifecycleOwner
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Repository to observe the current app life cycle state.
 */
@Singleton
class DefaultAppLifecycleRepository
  @Inject
  constructor() : AppLifecycleRepository {
    private val appLifecycleState = MutableSharedFlow<AppLifecycleState>(extraBufferCapacity = 1)

    init {
      ProcessLifecycleOwner.get().lifecycle.addObserver(AppLifecycleObserver(appLifecycleState))
    }

    override fun observeLifecycle(): Flow<AppLifecycleState> = appLifecycleState
  }
