package nl.rijksoverheid.mgo.data.fhirParser.js

import android.content.Context
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Wrapper class for the V8 JavaScript runtime (J2V8) used to execute JavaScript code in the application.
 * This class ensures that all interactions with the V8 engine occur on a single thread, as required by J2V8.
 * It also integrates coroutine support for asynchronous execution.
 *
 * @param context The Android application context.
 */
@SuppressWarnings("all")
@Singleton
internal class DefaultJsRuntimeRepository
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
  ) : JsRuntimeRepository {
    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    private val v8Dispatcher: ExecutorCoroutineDispatcher = newSingleThreadContext("V8Thread")
    private val jsRuntime: MutableStateFlow<V8?> = MutableStateFlow(null)

    /**
     * Loads the JavaScript file used for shared functionality across Web, iOS, and Android clients.
     * Since this file is large, it should ideally be loaded during app launch for performance reasons.
     * Once loaded, the [jsRuntime] emits the initialized V8 runtime instance.
     */
    @OptIn(ExperimentalCoroutinesApi::class)
    override suspend fun load() {
      withContext(v8Dispatcher) {
        val jsCode =
          context.assets.open("mgo-hcim-api.iife.js").bufferedReader().use { reader ->
            reader.readText()
          }
        val runtime = V8.createV8Runtime()
        runtime.executeVoidScript(jsCode)
        jsRuntime.value = runtime
      }
    }

    /**
     * Executes a JavaScript function.
     *
     * @param name The name of the JavaScript function to call.
     * @param parameters A list of string parameters to pass to the function.
     * @return The string result of the function execution.
     */
    override suspend fun executeStringFunction(
      name: String,
      parameters: List<String>,
    ): String =
      withContext(v8Dispatcher) {
        val v8 = get()
        val hcimApi = v8.getObject("HcimApi")
        val v8Parameters = v8.createParameters(parameters)
        hcimApi.executeStringFunction(name, v8Parameters)
      }

    /**
     * Converts a list of string parameters into a V8Array, which can be passed to JavaScript functions.
     *
     * @param parameters A list of string values to be converted.
     * @return A V8Array containing the provided parameters.
     */
    private fun V8.createParameters(parameters: List<String>): V8Array {
      val array = V8Array(this)
      parameters.forEach { parameter ->
        array.push(parameter)
      }
      return array
    }

    /**
     * Retrieves the initialized V8 runtime instance.
     * Suspends execution until the runtime is available.
     *
     * @return The initialized V8 runtime instance.
     */
    private suspend fun get(): V8 =
      withContext(v8Dispatcher) {
        jsRuntime.filterNotNull().first()
      }
  }
