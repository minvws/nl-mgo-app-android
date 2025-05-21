package nl.rijksoverheid.mgo.data.fhirParser.mgoResource

import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.framework.util.base64.TestBase64Util
import org.junit.Assert.assertEquals
import org.junit.Test

class DefaultMgoResourceMapperTest {
  private val base64Util = TestBase64Util()
  private val jsRuntimeRepository = mockk<JsRuntimeRepository>()
  private val mgoResourceMapper = DefaultMgoResourceMapper(jsRuntimeRepository, base64Util)

  @Test
  fun testMapMgoResource() =
    runTest {
      // Given: js function returns array with json objects first call
      // and mgo resources second call
      coEvery {
        jsRuntimeRepository.executeStringFunction(any(), any())
      } returns "[{}, {}]" andThen "{\"referenceId\":\"1\"," +
        "\"profile\":\"profile\"}"

      // When: calling get
      val resources = mgoResourceMapper.get(fhirBundleJson = "", fhirVersion = FhirVersion.R4)

      // Then: Return two mgo resources
      assertEquals(2, resources.size)
    }
}
