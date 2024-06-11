package nl.rijksoverheid.mgo.framework.fhirextension

import org.hl7.fhir.dstu3.model.DateTimeType
import org.hl7.fhir.dstu3.model.Dosage
import org.hl7.fhir.dstu3.model.Extension
import org.hl7.fhir.dstu3.model.MedicationStatement
import org.hl7.fhir.dstu3.model.Period
import org.hl7.fhir.dstu3.model.Reference
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

class MedicationStatementTest {
    @Test
    fun `Given no errors, When calling getTitleString, Then return title`() {
        // Given
        val medicationStatement =
            MedicationStatement().apply {
                setMedication(Reference().apply { setDisplay("Hello World") })
            }

        // When
        val title = medicationStatement.getTitleString()

        // Then
        assertEquals("Hello World", title)
    }

    @Test
    fun `Given error, When calling getTitleString, Then return null`() {
        // Given
        val medicationStatement = MedicationStatement()

        // When
        val title = medicationStatement.getTitleString()

        // Then
        assertEquals(null, title)
    }

    @Test
    fun `Given no dosage, When calling getInstructionsString, Then return null`() {
        // Given
        val medicationStatement = MedicationStatement().setDosage(listOf())

        // When
        val instructions = medicationStatement.getInstructionsString()

        // Then
        assertEquals(null, instructions)
    }

    @Test
    fun `Given dosage, When calling getInstructionsString, Then return instructions`() {
        // Given
        val medicationStatement =
            MedicationStatement().setDosage(
                listOf(
                    Dosage().apply { setText("Dosage 1") },
                    Dosage().apply { setText("Dosage 2") },
                ),
            )

        // When
        val comment = medicationStatement.getInstructionsString()

        // Then
        assertEquals("Dosage 1, Dosage 2", comment)
    }

    @Test
    fun `Given no errors, When calling getPrescribedByString, Then return prescribed by`() {
        // Given
        val medicationStatement =
            MedicationStatement().apply {
                setExtension(
                    listOf(
                        Extension().apply {
                            setUrl("http://nictiz.nl/fhir/StructureDefinition/zib-MedicationUse-Prescriber")
                            setValue(
                                Reference().apply {
                                    setDisplay("Dokter")
                                },
                            )
                        },
                    ),
                )
            }

        // When
        val prescribedBy = medicationStatement.getPrescribedByString()

        // Then
        assertEquals("Dokter", prescribedBy)
    }

    @Test
    fun `Given error, When calling getPrescribedByString, Then return null`() {
        // Given
        val medicationStatement =
            MedicationStatement().apply {
                setExtension(
                    listOf(
                        Extension().apply {
                            setUrl("http://google.nl")
                        },
                    ),
                )
            }

        // When
        val prescribedBy = medicationStatement.getPrescribedByString()

        // Then
        assertEquals(null, prescribedBy)
    }

    @Test
    fun `Given no errors, When calling getLowercaseStatusString, Then return status`() {
        // Given
        val medicationStatement =
            MedicationStatement().apply {
                setStatus(MedicationStatement.MedicationStatementStatus.ACTIVE)
            }

        // When
        val status = medicationStatement.getLowercaseStatusString()

        // Then
        assertEquals("active", status)
    }

    @Test
    fun `Given error, When calling getLowercaseStatusString, Then return null`() {
        // Given
        val medicationStatement = MedicationStatement()

        // When
        val status = medicationStatement.getLowercaseStatusString()

        // Then
        assertEquals(null, status)
    }

    @Test
    fun `Given no errors, When calling getStartDate, Then return date`() {
        // Given
        val instant = LocalDateTime.of(2024, 1, 5, 0, 0).toInstant(ZoneOffset.UTC)
        val medicationStatement =
            MedicationStatement().apply {
                setEffective(Period().apply { setStartElement(DateTimeType(Date.from(instant))) })
            }

        // When
        val startDate = medicationStatement.getStartDateString()

        // Then
        assertEquals("2024-01-05T01:00:00+01:00", startDate)
    }

    @Test
    fun `Given error, When calling getStartDate, Then return null`() {
        // Given
        val medicationStatement = MedicationStatement()

        // When
        val startDate = medicationStatement.getStartDateString()

        // Then
        assertEquals(null, startDate)
    }
}
