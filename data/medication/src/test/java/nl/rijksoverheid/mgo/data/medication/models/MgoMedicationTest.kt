package nl.rijksoverheid.mgo.data.medication.models

import ca.uhn.fhir.context.FhirContext
import nl.rijksoverheid.mgo.framework.test.getJsonFromResources
import org.hl7.fhir.dstu3.model.Bundle
import org.hl7.fhir.dstu3.model.MedicationStatement
import org.junit.Assert.assertEquals
import org.junit.Test

internal class MgoMedicationTest {
    private val context = FhirContext.forDstu3()
    private val parser = context.newJsonParser()

    @Test
    fun `Given a medication statement, when mapping to a medication, then all values are mapped correctly`() {
        // Given
        val medicationStatementJson = getJsonFromResources("response/medicationstatement.json")
        val bundle = parser.parseResource(Bundle::class.java, medicationStatementJson)
        val medicationStatement = bundle.entry.first().resource as MedicationStatement

        // When
        val medication = medicationStatement.toMedication()

        // Then
        assertEquals("Zestril tablet 10mg", medication.title)
        assertEquals("1 maal per dag 1 tablet, oraal", medication.instructions)
        assertEquals("Huisartsen, niet nader gespecificeerd", medication.prescribedBy)
        assertEquals("2018-06-28", medication.startDate)
        assertEquals("Active", medication.status)
    }

    @Test
    fun `Given a medication statement has no Period, when mapping to a medication, then show empty string`() {
        // Given
        val medicationStatementJson = getJsonFromResources("response/medicationstatement.json")
        val bundle = parser.parseResource(Bundle::class.java, medicationStatementJson)
        val medicationStatement = bundle.entry.first().resource as MedicationStatement
        medicationStatement.setEffective(null)

        // When
        val medication = medicationStatement.toMedication()

        // Then
        assertEquals("", medication.startDate)
    }

    @Test
    fun `Given a medication statement has missing extension, when mapping to a medication, then show empty string`() {
        // Given
        val medicationStatementJson = getJsonFromResources("response/medicationstatement.json")
        val bundle = parser.parseResource(Bundle::class.java, medicationStatementJson)
        val medicationStatement = bundle.entry.first().resource as MedicationStatement
        medicationStatement.setExtension(null)

        // When
        val medication = medicationStatement.toMedication()

        // Then
        assertEquals("", medication.prescribedBy)
    }

    @Test
    fun `Given a medication statement has missing display, when mapping to a medication, then show empty string`() {
        // Given
        val medicationStatementJson = getJsonFromResources("response/medicationstatement.json")
        val bundle = parser.parseResource(Bundle::class.java, medicationStatementJson)
        val medicationStatement = bundle.entry.first().resource as MedicationStatement
        medicationStatement.medicationReference.setDisplay(null)

        // When
        val medication = medicationStatement.toMedication()

        // Then
        assertEquals("", medication.title)
    }
}
