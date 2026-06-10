package nl.rijksoverheid.mgo.data.hcimParser

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.framework.javascript.ExecuteJavascript
import nl.rijksoverheid.mgo.framework.javascript.JvmJavascriptEngineRepository

object UiSchemaParserFactory {
  @OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
  suspend fun createForJvm(dispatcher: CoroutineDispatcher = newSingleThreadContext("QuickJsThread")): UiSchemaParser {
    val javascriptEngineRepository = JvmJavascriptEngineRepository(dispatcher)
    javascriptEngineRepository.create()
    javascriptEngineRepository.load("mgo-hcim-api.iife.js")
    val executeJavaScript = ExecuteJavascript(javascriptEngineRepository)
    return UiSchemaParser(executeJavaScript)
  }
}
