package nl.rijksoverheid.mgo.data.hcimParser.javascript

import kotlinx.coroutines.withContext
import javax.inject.Inject

class JsEngineRepository
  @Inject
  constructor(
    private val quickJsRepository: QuickJsRepository,
  ) {
    suspend fun executeStringFunction(
      functionName: String,
      parameters: List<String>,
    ): String =
      withContext(quickJsRepository.quickJsDispatcher) {
        // Get the Quick JS instance
        val quickJs = quickJsRepository.get()

        // Create function call
        val globalObject = quickJs.globalObject
        val hcimApi = globalObject.getJSObject("HcimApi")
        val functionCall = hcimApi.getJSFunction(functionName)

        // Execute function call
        val returnedString = functionCall.call(*parameters.toTypedArray()) as String

        // Release objects
        functionCall.release()

        // Return the output of the function
        returnedString
      }
  }
