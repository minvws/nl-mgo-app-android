package nl.rijksoverheid.mgo.data.hcimParser.javascript

import javax.inject.Inject

class JsEngineRepository
  @Inject
  constructor(
    private val quickJsRepository: QuickJsRepository,
  ) {
    fun executeStringFunction(
      functionName: String,
      parameters: List<String>,
    ): String {
      // Get the Quick JS instance
      val quickJs = quickJsRepository.get()

      // Create function call
      val globalObject = quickJs.globalObject
      val hcimApi = globalObject.getJSObject("HcimApi")
      val functionCall = hcimApi.getJSFunction(functionName)

      // Execute function call
      return functionCall.call(*parameters.toTypedArray()) as String
    }
  }
