package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.TEST_MGO_RESOURCE
import nl.rijksoverheid.mgo.framework.util.base64.TestBase64Util
import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultUiSchemaMapperTest {
  private val base64Util = TestBase64Util()
  private val jsRuntimeRepository = mockk<JsRuntimeRepository>()
  private val uiSchemaMapper = DefaultUiSchemaMapper(jsRuntimeRepository, base64Util)

  @Test
  fun testGetSummary() =
    runTest {
      // Given: js function returns ui schema json
      coEvery {
        jsRuntimeRepository.executeStringFunction(any(), any())
      } returns "{\"label\":\"label\",\"children\":[]}"

      // When: calling get
      val uiSchema = uiSchemaMapper.getSummary(mgoResource = TEST_MGO_RESOURCE)

      // Then: UI Schema is parsed
      assertEquals("label", uiSchema.label)
    }

  @Test
  fun testGetDetail() =
    runTest {
      // Given: js function returns ui schema json
      coEvery {
        jsRuntimeRepository.executeStringFunction(any(), any())
      } returns "{\"label\":\"label\",\"children\":[]}"

      // When: calling get
      val uiSchema = uiSchemaMapper.getDetail(mgoResource = TEST_MGO_RESOURCE)

      // Then: UI Schema is parsed
      assertEquals("label", uiSchema.label)
    }
}
