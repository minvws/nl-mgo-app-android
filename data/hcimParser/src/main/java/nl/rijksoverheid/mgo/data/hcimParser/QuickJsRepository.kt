package nl.rijksoverheid.mgo.data.hcimParser

import com.whl.quickjs.wrapper.QuickJSContext

interface QuickJsRepository {
  fun create()

  fun get(): QuickJSContext
}
