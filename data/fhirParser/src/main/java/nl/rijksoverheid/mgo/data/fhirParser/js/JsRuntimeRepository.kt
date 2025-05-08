package nl.rijksoverheid.mgo.data.fhirParser.js

/**
 * Wrapper class for the V8 JavaScript runtime (J2V8) used to execute JavaScript code in the application.
 */
interface JsRuntimeRepository {
  /**
   * Loads the JavaScript file used for shared functionality across Web, iOS, and Android clients.
   */
  suspend fun load()

  /**
   * Executes a JavaScript function.
   *
   * @param name The name of the JavaScript function to call.
   * @param parameters A list of string parameters to pass to the function.
   * @return The string result of the function execution.
   */
  suspend fun executeStringFunction(
    name: String,
    parameters: List<String>,
  ): String
}
