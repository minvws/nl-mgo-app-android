package nl.rijksoverheid.mgo.data.hcimParser

import timber.log.Timber
import javax.inject.Inject

class DefaultJsEngineRepository
  @Inject
  constructor(
    private val quickJsRepository: QuickJsRepository,
  ) : JsEngineRepository {
    override suspend fun executeStringFunction(
      functionName: String,
      parameters: List<String>,
    ): String {
      // Get the Quick JS instance
      val quickJs = quickJsRepository.get()

      // Create function call
      val escapedParams = parameters.joinToString(",")
      val functionCall = "HcimApi.$functionName(\"$escapedParams\");"

      // Execute function call
      return quickJs.evaluate(functionCall) as String
    }
  }
