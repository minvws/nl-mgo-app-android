package nl.rijksoverheid.mgo.data.fhirParser.js

import android.content.Context
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

/**
 * Wrapper class for the v8 javascript runtime we use to parse javascript (https://github.com/eclipsesource/J2V8).
 * This class wraps some function from it to make sure they work with coroutines,
 * and everything is executed on the same thread (which is a requirement of j2v8).
 */
@SuppressWarnings("all")
@Singleton
internal class DefaultJsRuntimeRepository
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : JsRuntimeRepository {
        @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
        private val v8Dispatcher = newSingleThreadContext("V8Thread")
        private val jsRuntime: MutableStateFlow<V8?> = MutableStateFlow(null)

        /**
         * Loads the javascript file we use to share code between the Web, iOS and Android clients.
         * This is a pretty big file, so preferable load this during app launch.
         * When loaded it will emit to [jsRuntime] so the runtime can be accessed.
         */
        override suspend fun load() {
            withContext(v8Dispatcher) {
                val reader1 = BufferedReader(InputStreamReader(context.assets.open("mgo-fhir-data.iife.js")))
                val jsCode = reader1.use { it.readText() }
                val runtime = V8.createV8Runtime()
                runtime.executeVoidScript(jsCode)
                jsRuntime.value = runtime
            }
        }

        /**
         * Calls javascript function that returns a string.
         * @param name The name of the javascript function.
         * @param parameters List of parameters you want to send with the function.
         */
        override suspend fun executeStringFunction(
            name: String,
            parameters: List<String>,
        ): String {
            return withContext(v8Dispatcher) {
                val v8 = get()
                val mgoFhirData = v8.getObject("MgoFhirData")
                val v8Parameters = v8.createParameters(parameters)
                mgoFhirData.executeStringFunction(name, v8Parameters)
            }
        }

        private fun V8.createParameters(parameters: List<String>): V8Array {
            val array = V8Array(this)
            parameters.forEach { parameter ->
                array.push(parameter)
            }
            return array
        }

        private suspend fun get(): V8 {
            return withContext(v8Dispatcher) {
                jsRuntime.filterNotNull().first()
            }
        }
    }
