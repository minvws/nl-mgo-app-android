package nl.rijksoverheid.mgo.data.fhirParser.mgoResource

import io.mockk.coEvery
import io.mockk.mockk
import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import kotlinx.coroutines.test.runTest

class DefaultMgoResourceMapperTest {
    private val jsRuntimeRepository = mockk<JsRuntimeRepository>()
    private val mgoResourceMapper = DefaultMgoResourceMapper(jsRuntimeRepository)

    @Test
    fun testMapMgoResource() =
        runTest {
            // Given: js function returns array with json objects first call
            // and mgo resources second call
            coEvery { jsRuntimeRepository.executeStringFunction(any(), any()) } returns "[{}, {}]" andThen "{\"referenceId\":\"1\"," +
                "\"profile\":\"profile\"}"

            // When: calling get
            val resources = mgoResourceMapper.get(fhirBundleJson = "", fhirVersion = FhirVersion.R4)

            // Then: Return two mgo resources
            assertEquals(2, resources.size)
        }
}
