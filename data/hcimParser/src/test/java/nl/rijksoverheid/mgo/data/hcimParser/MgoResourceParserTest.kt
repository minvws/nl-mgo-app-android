package nl.rijksoverheid.mgo.data.hcimParser

import nl.rijksoverheid.mgo.data.hcimParser.fhir.FhirVersion
import nl.rijksoverheid.mgo.data.hcimParser.javascript.JsEngineRepository
import nl.rijksoverheid.mgo.data.hcimParser.mgoResource.MgoResourceParser
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class MgoResourceParserTest {
  private val jvmQuickJsRepository = JvmQuickJsRepository()
  private val jsEngineRepository = JsEngineRepository(jvmQuickJsRepository)
  private val mgoResourceParser = MgoResourceParser(jsEngineRepository)

  @Before
  fun before() {
    jvmQuickJsRepository.create()
  }

  @Test
  fun testMgoResourceParser() {
    // Given: A fhir response
    val fhirResponse =
      this::class.java.classLoader
        ?.getResource("fhir-Consent.json")!!
        .readText(Charsets.UTF_8)

    // When: Calling the parser
    val mgoResources = mgoResourceParser.invoke(fhirResponse = fhirResponse, fhirVersion = FhirVersion.R3)

    // Then: Mgo resources are returned
    assertEquals(1, mgoResources.size)
    assertEquals("Consent/db0e91bf-a767-489c-9bca-36dcfbc10241", mgoResources[0].referenceId)
  }
}
