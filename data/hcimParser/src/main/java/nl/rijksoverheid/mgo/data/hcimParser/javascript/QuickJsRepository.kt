package nl.rijksoverheid.mgo.data.hcimParser.javascript

import com.whl.quickjs.wrapper.QuickJSContext
import kotlinx.coroutines.CoroutineDispatcher

interface QuickJsRepository {
  val quickJsDispatcher: CoroutineDispatcher

  suspend fun create()

  suspend fun get(): QuickJSContext
}
