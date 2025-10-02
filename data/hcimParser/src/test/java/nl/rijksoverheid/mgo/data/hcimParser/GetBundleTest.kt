package nl.rijksoverheid.mgo.data.hcimParser

import kotlinx.coroutines.test.runTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class GetBundleTest {
  private val jvmQuickJsRepository = JvmQuickJsRepository()
  private val jsEngineRepository = DefaultJsEngineRepository(jvmQuickJsRepository)

  @Before
  fun before() {
    jvmQuickJsRepository.create()
  }

  @Test
  fun testGetBundle() =
    runTest {
      val fhirJson =
        this::class.java.classLoader
          ?.getResource("fhir.json")!!
          .readText(Charsets.UTF_8)
      val safeFhirJson = escapeJsonForJs(fhirJson)

      val output = jsEngineRepository.executeStringFunction(functionName = "getBundleResourcesJson", parameters = listOf(safeFhirJson))
      println(output)
      Assert.assertNotNull(output)
    }
}

fun escapeJsonForJs(json: String): String {
  return json
    .replace("\\", "\\\\") // backslash escapen
    .replace("\"", "\\\"") // dubbele quotes escapen
    .replace("\n", "\\n") // nieuwe regels escapen
    .replace("\r", "\\r") // carriage return escapen
    .replace("\t", "\\t") // tabs escapen
}
