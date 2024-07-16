package nl.rijksoverheid.mgo.data.api.dva

import ca.uhn.fhir.context.FhirContext
import nl.rijksoverheid.mgo.framework.test.TEST_OKHTTP_CLIENT
import nl.rijksoverheid.mgo.framework.test.rules.TestServerRule
import org.hl7.fhir.dstu3.model.Bundle
import org.hl7.fhir.dstu3.model.MedicationStatement
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import kotlinx.coroutines.test.runTest

class DvaApiTest {
    @get:Rule
    val testServerRule = TestServerRule()

    private val context = FhirContext.forDstu3()
    private val testServer = testServerRule.testServer

    @Test
    fun `Given response with empty bundle, when calling medicationStatement, return empty list`() =
        runTest {
            // Given
            val bundle = Bundle()
            val jsonParser = context.newJsonParser()
            val bundleJson = jsonParser.encodeResourceToString(bundle)
            testServer.enqueueJson(bundleJson)

            // When
            val dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url())
            val medicationStatements = dvaApi.medicationStatement("")

            // Then
            assertEquals(listOf<MedicationStatement>(), medicationStatements)
        }

    @Test
    fun `Given response with empty bundle, when calling condition, return empty list`() =
        runTest {
            // Given
            val bundle = Bundle()
            val jsonParser = context.newJsonParser()
            val bundleJson = jsonParser.encodeResourceToString(bundle)
            testServer.enqueueJson(bundleJson)

            // When
            val dvaApi = createDvaApi(okHttpClient = TEST_OKHTTP_CLIENT, baseUrl = testServer.url())
            val medicationStatements = dvaApi.condition("")

            // Then
            assertEquals(listOf<MedicationStatement>(), medicationStatements)
        }
}
