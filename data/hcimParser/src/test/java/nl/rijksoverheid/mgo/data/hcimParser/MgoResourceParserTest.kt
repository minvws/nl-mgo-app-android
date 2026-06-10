package nl.rijksoverheid.mgo.data.hcimParser

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceParser
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion
import nl.rijksoverheid.mgo.framework.javascript.ExecuteJavascript
import nl.rijksoverheid.mgo.framework.javascript.JvmJavascriptEngineRepository
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MgoResourceParserTest {
  private val javascriptEngineRepository = JvmJavascriptEngineRepository()
  private val executeJavascript = ExecuteJavascript(javascriptEngineRepository)
  private val mgoResourceParser = MgoResourceParser(executeJavascript)

  @Before
  fun before() =
    runTest {
      javascriptEngineRepository.create()
      javascriptEngineRepository.load("mgo-hcim-api.iife.js")
    }

  @Test
  fun testMgoResourceParser() =
    runTest {
      // Given: A fhir response
      val fhirResponse =
        this::class.java.classLoader
          ?.getResource("fhir-Consent.json")!!
          .readText(Charsets.UTF_8)

      // When: Calling the parser
      val mgoResources = mgoResourceParser.invoke(fhirResponse = fhirResponse, fhirVersion = FhirVersion.R3, organizationId = "1", organizationName = "")

      // Then: Mgo resources are returned
      assertEquals(1, mgoResources.size)
      assertEquals("Consent/db0e91bf-a767-489c-9bca-36dcfbc10241", mgoResources[0].referenceId)
    }
}
