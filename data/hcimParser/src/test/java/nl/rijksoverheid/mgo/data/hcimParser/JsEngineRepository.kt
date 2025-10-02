package nl.rijksoverheid.mgo.data.hcimParser

interface JsEngineRepository {
  suspend fun executeStringFunction(
    functionName: String,
    parameters: List<String>,
  ): String
}
