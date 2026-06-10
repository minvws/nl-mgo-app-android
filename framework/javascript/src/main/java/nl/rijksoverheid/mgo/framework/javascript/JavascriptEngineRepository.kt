package nl.rijksoverheid.mgo.framework.javascript

import com.whl.quickjs.wrapper.QuickJSContext
import kotlinx.coroutines.CoroutineDispatcher

interface JavascriptEngineRepository {
  val quickJsDispatcher: CoroutineDispatcher

  suspend fun create()

  suspend fun load(fileName: String)

  suspend fun get(): QuickJSContext
}
