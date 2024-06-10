package nl.rijksoverheid.mgo.data.medication.models

import nl.rijksoverheid.mgo.data.api.dva.FhirMapper
import nl.rijksoverheid.mgo.framework.test.getJsonFromResources
import nl.rijksoverheid.mgo.framework.test.jsonStringToList
import org.hl7.fhir.dstu3.model.MedicationStatement
import org.junit.Assert.assertEquals
import org.junit.Test

internal class MgoMedicationTest {
    private val fhirMapper = FhirMapper()

    @Test
    fun `Given MedicationStatement, When mapping to MgoMedication, Model is same as expected json`() {
        // Given
        val medications =
            fhirMapper
                .toDomainResources(getJsonFromResources("medication_statement_input_1.json"))
                .filterIsInstance<MedicationStatement>()

        // When
        val mgoConcerns = medications.map { medication -> medication.toMedication() }

        // Then
        val expectedConcerns = getJsonFromResources("medication_statement_output_1.json").jsonStringToList<MgoMedication>()
        assertEquals(expectedConcerns, mgoConcerns)
    }
}
