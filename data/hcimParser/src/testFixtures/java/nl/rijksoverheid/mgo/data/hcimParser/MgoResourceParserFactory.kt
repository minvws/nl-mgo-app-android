package nl.rijksoverheid.mgo.data.hcimParser

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.newSingleThreadContext
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceParser
import nl.rijksoverheid.mgo.framework.javascript.ExecuteJavascript
import nl.rijksoverheid.mgo.framework.javascript.JvmJavascriptEngineRepository

object MgoResourceParserFactory {
  @OptIn(DelicateCoroutinesApi::class, ExperimentalCoroutinesApi::class)
  suspend fun createForJvm(dispatcher: CoroutineDispatcher = newSingleThreadContext("QuickJsThread")): MgoResourceParser {
    val javascriptEngineRepository = JvmJavascriptEngineRepository(dispatcher)
    javascriptEngineRepository.create()
    javascriptEngineRepository.load("mgo-hcim-api.iife.js")
    val executeJavaScript = ExecuteJavascript(javascriptEngineRepository)
    return MgoResourceParser(executeJavaScript)
  }
}
