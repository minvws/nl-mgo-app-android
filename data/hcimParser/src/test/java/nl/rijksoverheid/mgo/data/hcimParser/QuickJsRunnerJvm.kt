package nl.rijksoverheid.mgo.data.hcimParser

import com.whl.quickjs.wrapper.QuickJSContext
import com.whl.quickjs.wrapper.QuickJSException

class QuickJsRunnerJvm {
  fun callGetBundleResourcesJson(): String {
    // 1️⃣ JSON uit resources lezen
    val fhirJson =
      this::class.java.classLoader
        .getResource("fhir.json")!!
        .readText(Charsets.UTF_8)

    // 2️⃣ JS code uit resources lezen
    val jsCode =
      this::class.java.classLoader
        .getResource("script.js")!!
        .readText(Charsets.UTF_8)

    // 3️⃣ QuickJS context aanmaken
    val jsContext = QuickJSContext.create()
    jsContext.setMaxStackSize(16 * 1024 * 1024)
    try {
      // 4️⃣ Evalueren van script.js
      jsContext.evaluate(jsCode)

      // 5️⃣ JSON veilig encoderen voor inline JS
      val safeJson = escapeJsonForJs(fhirJson)
      val scriptCall = "HcimApi.getBundleResourcesJson(\"$safeJson\");"

      // 6️⃣ Functie aanroepen
      return jsContext.evaluate(scriptCall) as String
    } catch (e: QuickJSException) {
      e.printStackTrace()
      return ""
    } finally {
      jsContext.destroy()
    }
  }

  private fun escapeJsonForJs(json: String): String =
    json
      .replace("\\", "\\\\")
      .replace("\"", "\\\"")
      .replace("\n", "\\n")
      .replace("\r", "\\r")
      .replace("\t", "\\t")
}
