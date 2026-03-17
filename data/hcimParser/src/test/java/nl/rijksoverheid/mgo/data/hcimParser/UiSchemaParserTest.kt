package nl.rijksoverheid.mgo.data.hcimParser

import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.hcimParser.javascript.JsEngineRepository
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.UiSchemaParser
import nl.rijksoverheid.mgo.framework.test.readResourceFile
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class UiSchemaParserTest {
  private val jvmQuickJsRepository = JvmQuickJsRepository()
  private val jsEngineRepository = JsEngineRepository(jvmQuickJsRepository)
  private val uiSchemaParser = UiSchemaParser(jsEngineRepository)

  @Before
  fun before() =
    runTest {
      jvmQuickJsRepository.create()
    }

  @Test
  fun testGetCardDetails() =
    runTest {
      // Given: A mgo resource
      val mgoResource = readResourceFile("mgoResource-Consent.json")

      // When: Calling the parser
      val cardDetails = uiSchemaParser.getCardDetail(mgoResourceJson = mgoResource, organizationName = "Test Organization")

      // Then: Ui schema is returned
      assertEquals("db0e91bf-a767-489c-9bca-36dcfbc10241", cardDetails.title)
    }

  @Test
  fun testGetSummary() =
    runTest {
      // Given: A mgo resource
      val mgoResource = readResourceFile("mgoResource-Consent.json")

      // When: Calling the parser
      val healthUiSchema = uiSchemaParser.getSummary(mgoResourceJson = mgoResource, organizationName = "Test Organization")

      // Then: Ui schema is returned
      assertEquals("db0e91bf-a767-489c-9bca-36dcfbc10241", healthUiSchema.label)
    }

  @Test
  fun testGetDetail() =
    runTest {
      // Given: A mgo resource
      val mgoResource = readResourceFile("mgoResource-Consent.json")

      // When: Calling the parser
      val healthUiSchema = uiSchemaParser.getDetails(mgoResourceJson = mgoResource, organizationName = "Test Organization")

      // Then: Ui schema is returned
      assertEquals("Wilsverklaring", healthUiSchema.label)
    }
}
