package nl.rijksoverheid.mgo.data.hcimParser.javascript

import com.whl.quickjs.wrapper.QuickJSContext

interface QuickJsRepository {
  fun create()

  fun get(): QuickJSContext
}
