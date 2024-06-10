package nl.rijksoverheid.mgo.data.concern.models

import nl.rijksoverheid.mgo.data.api.dva.FhirMapper
import nl.rijksoverheid.mgo.framework.test.getJsonFromResources
import nl.rijksoverheid.mgo.framework.test.jsonStringToList
import org.hl7.fhir.dstu3.model.Condition
import org.junit.Assert.assertEquals
import org.junit.Test

internal class MgoConcernTest {
    private val fhirMapper = FhirMapper()

    @Test
    fun `Given Condition, When mapping to MgoConcern, Model is same as expected json`() {
        // Given
        val conditions =
            fhirMapper
                .toDomainResources(getJsonFromResources("condition_input.json"))
                .filterIsInstance<Condition>()

        // When
        val mgoConcerns = conditions.map { condition -> condition.toConcern() }

        // Then
        val expectedConcerns = getJsonFromResources("condition_output.json").jsonStringToList<MgoConcern>()
        assertEquals(expectedConcerns, mgoConcerns)
    }
}
