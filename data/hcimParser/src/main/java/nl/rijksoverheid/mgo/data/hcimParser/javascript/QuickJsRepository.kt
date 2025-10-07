package nl.rijksoverheid.mgo.data.hcimParser.javascript

import com.whl.quickjs.wrapper.QuickJSContext
import kotlinx.coroutines.ExecutorCoroutineDispatcher

interface QuickJsRepository {
  val quickJsDispatcher: ExecutorCoroutineDispatcher

  suspend fun create()

  suspend fun get(): QuickJSContext
}
