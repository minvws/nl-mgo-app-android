package nl.rijksoverheid.mgo.data.fhirParser.js

class TestJsRuntimeRepository : JsRuntimeRepository {
  private var loaded: Boolean = false

  override suspend fun load() {
    this.loaded = true
  }

  fun assertIsLoaded(): Boolean = loaded

  override suspend fun executeStringFunction(
    name: String,
    parameters: List<String>,
  ): String = ""
}
