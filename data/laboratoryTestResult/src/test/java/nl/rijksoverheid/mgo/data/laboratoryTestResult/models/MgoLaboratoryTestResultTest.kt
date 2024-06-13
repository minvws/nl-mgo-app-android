package nl.rijksoverheid.mgo.data.laboratoryTestResult.models

import nl.rijksoverheid.mgo.data.api.dva.FhirMapper
import nl.rijksoverheid.mgo.framework.test.getJsonFromResources
import nl.rijksoverheid.mgo.framework.test.jsonStringToList
import org.hl7.fhir.dstu3.model.Observation
import org.junit.Assert.assertEquals
import org.junit.Test

internal class MgoLaboratoryTestResultTest {
    private val fhirMapper = FhirMapper()

    @Test
    fun `Given Observation, When mapping to MgoObservation, Model is same as expected json`() {
        // Given
        val observations =
            fhirMapper
                .toDomainResources(getJsonFromResources("observation_input.json"))
                .filterIsInstance<Observation>()

        // When
        val mgoLaboratoryTestResults = observations.map { condition -> condition.toMgoLaboratoryTestResult() }

        // Then
        val expectedConcerns = getJsonFromResources("observation_output.json").jsonStringToList<MgoLaboratoryTestResult>()
        assertEquals(expectedConcerns, mgoLaboratoryTestResults)
    }
}
