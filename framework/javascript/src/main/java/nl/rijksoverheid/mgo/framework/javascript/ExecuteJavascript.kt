package nl.rijksoverheid.mgo.framework.javascript

import kotlinx.coroutines.withContext
import javax.inject.Inject

class ExecuteJavascript
  @Inject
  constructor(
    private val javascriptEngineRepository: JavascriptEngineRepository,
  ) {
    suspend operator fun invoke(
      objectName: String,
      functionName: String,
      parameters: List<String>,
    ): String =
      withContext(javascriptEngineRepository.quickJsDispatcher) {
        // Get the Quick JS instance
        val quickJs = javascriptEngineRepository.get()

        // Create function call
        val globalObject = quickJs.globalObject
        val jsObject = globalObject.getJSObject(objectName)
        val functionCall = jsObject.getJSFunction(functionName)

        // Execute function call
        val returnedString = functionCall.call(*parameters.toTypedArray()) as String

        // Release objects
        functionCall.release()

        // Return the output of the function
        returnedString
      }
  }
