package nl.rijksoverheid.mgo.lifecycle

import kotlinx.coroutines.flow.Flow

/**
 * Repository to observe the current app life cycle state.
 */
interface AppLifecycleRepository {
  fun observeLifecycle(): Flow<AppLifecycleState>
}
