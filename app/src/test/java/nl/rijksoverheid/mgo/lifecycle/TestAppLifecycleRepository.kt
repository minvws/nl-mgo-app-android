package nl.rijksoverheid.mgo.lifecycle

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class TestAppLifecycleRepository : AppLifecycleRepository {
  override fun observeLifecycle(): Flow<AppLifecycleState> = flowOf()
}
