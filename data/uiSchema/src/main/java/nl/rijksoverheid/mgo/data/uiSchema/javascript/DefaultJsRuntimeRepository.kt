package nl.rijksoverheid.mgo.data.uiSchema.javascript

import android.content.Context
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import dagger.hilt.android.qualifiers.ApplicationContext
import timber.log.Timber
import java.io.BufferedReader
import java.io.InputStreamReader
import java.time.Instant
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext

@Singleton
internal class DefaultJsRuntimeRepository
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : JsRuntimeRepository {
        private val v8Dispatcher = newSingleThreadContext("V8Thread")
        private val jsRuntime: MutableStateFlow<V8?> = MutableStateFlow(null)

        override suspend fun load() {
            withContext(v8Dispatcher) {
                // Load js file
                val reader1 = BufferedReader(InputStreamReader(context.assets.open("mgo-fhir-data.iife.js")))
                val jsCode = reader1.use { it.readText() }

                // Create the javascript runtime
                val runtime = V8.createV8Runtime()

                // Parse javascript
                runtime.executeVoidScript(jsCode)

                // Set runtime in flow
                jsRuntime.value = runtime
            }
        }

        override suspend fun get(): V8 {
            return withContext(v8Dispatcher) {
                jsRuntime.filterNotNull().first()
            }
        }

        override suspend fun createParameters(parameters: List<String>): V8Array {
            return withContext(v8Dispatcher) {
                val v8 = get()
                val array = V8Array(v8)
                parameters.forEach { parameter ->
                    array.push(parameter)
                }
                array
            }
        }

        override suspend fun executeStringFunction(
            name: String,
            parameters: V8Array,
        ): String {
            return withContext(v8Dispatcher) {
                val v8 = get()
                val mgoFhirData = v8.getObject("MgoFhirData")
                mgoFhirData.executeStringFunction(name, parameters)
            }
        }
    }
