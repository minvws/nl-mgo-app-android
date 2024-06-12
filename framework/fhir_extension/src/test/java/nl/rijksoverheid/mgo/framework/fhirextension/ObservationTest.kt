package nl.rijksoverheid.mgo.framework.fhirextension

import org.hl7.fhir.dstu3.model.CodeableConcept
import org.hl7.fhir.dstu3.model.Coding
import org.hl7.fhir.dstu3.model.DateTimeType
import org.hl7.fhir.dstu3.model.Observation
import org.hl7.fhir.dstu3.model.Quantity
import org.hl7.fhir.dstu3.model.Reference
import org.hl7.fhir.dstu3.model.SimpleQuantity
import org.hl7.fhir.dstu3.model.Specimen
import org.junit.Assert.assertEquals
import org.junit.Test

class ObservationTest {

    @Test
    fun `Given no errors, When calling getTitleString, Then return title`() {
        // Given
        val observation =
            Observation().apply {
                addCategory(
                    CodeableConcept().apply {
                        addCoding(
                            Coding().apply {
                                setDisplay("Hello World")
                            },
                        )
                    },
                )
            }

        // When
        val title = observation.getTitleString()

        // Then
        assertEquals("Hello World", title)
    }

    @Test
    fun `Given error, When calling getTitleString, Then return null`() {
        // Given
        val observation = Observation()

        // When
        val title = observation.getTitleString()

        // Then
        assertEquals(null, title)
    }

    @Test
    fun `Given no errors, When calling getCodeString, Then return code`() {
        // Given
        val observation =
            Observation().apply {
                setCode(
                    CodeableConcept().apply {
                        addCoding(
                            Coding().apply {
                                setDisplay("Hello World")
                            },
                        )
                    },
                )
            }

        // When
        val code = observation.getCodeString()

        // Then
        assertEquals("Hello World", code)
    }

    @Test
    fun `Given error, When calling getCodeString, Then return null`() {
        // Given
        val observation = Observation()

        // When
        val code = observation.getCodeString()

        // Then
        assertEquals(null, code)
    }

    @Test
    fun `Given no errors, When calling getStatusString, Then return status`() {
        // Given
        val observation =
            Observation().apply {
                setStatus(Observation.ObservationStatus.FINAL)
            }

        // When
        val status = observation.getStatusString()

        // Then
        assertEquals("final", status)
    }

    @Test
    fun `Given error, When calling getStatusString, Then return null`() {
        // Given
        val observation = Observation()

        // When
        val status = observation.getStatusString()

        // Then
        assertEquals(null, status)
    }

    @Test
    fun `Given no errors, When calling getDateTimeString, Then return date`() {
        // Given
        val observation =
            Observation().apply {
                setEffective(DateTimeType("2012-05-23T12:00:00+02:00"))
            }

        // When
        val date = observation.getDateTimeString()

        // Then
        assertEquals("2012-05-23T12:00:00+02:00", date)
    }

    @Test
    fun `Given error, When calling getDateTimeString, Then return null`() {
        // Given
        val observation = Observation()

        // When
        val date = observation.getDateTimeString()

        // Then
        assertEquals(null, date)
    }

    @Test
    fun `Given no errors, When calling getResultString, Then return result`() {
        // Given
        val observation =
            Observation().apply {
                setValue(
                    Quantity().apply {
                        setValue(1.0)
                        setUnit("kg")
                    }
                )
            }

        // When
        val result = observation.getResultString()

        // Then
        assertEquals("1.0 kg", result)
    }

    @Test
    fun `Given error, When calling getResultString, Then return null`() {
        // Given
        val observation = Observation()

        // When
        val result = observation.getResultString()

        // Then
        assertEquals(null, result)
    }

    @Test
    fun `Given no errors, When calling getReferenceRangeLowString, Then return reference`() {
        // Given
        val observation =
            Observation().apply {
                addReferenceRange(
                    Observation.ObservationReferenceRangeComponent().apply {
                        setLow(
                            SimpleQuantity().apply {
                                setValue(1.0)
                                setUnit("kg")
                            }
                        )
                    }
                )
            }

        // When
        val low = observation.getReferenceRangeLowString()

        // Then
        assertEquals("1.0 kg", low)
    }

    @Test
    fun `Given error, When calling getReferenceRangeLowString, Then return null`() {
        // Given
        val observation = Observation()

        // When
        val low = observation.getReferenceRangeLowString()

        // Then
        assertEquals(null, low)
    }

    @Test
    fun `Given no errors, When calling getReferenceRangeHighString, Then return reference`() {
        // Given
        val observation =
            Observation().apply {
                addReferenceRange(
                    Observation.ObservationReferenceRangeComponent().apply {
                        setHigh(
                            SimpleQuantity().apply {
                                setValue(2.0)
                                setUnit("mg")
                            }
                        )
                    }
                )
            }

        // When
        val high = observation.getReferenceRangeHighString()

        // Then
        assertEquals("2.0 mg", high)
    }

    @Test
    fun `Given error, When calling getReferenceRangeHighString, Then return null`() {
        // Given
        val observation = Observation()

        // When
        val high = observation.getReferenceRangeHighString()

        // Then
        assertEquals(null, high)
    }

    @Test
    fun `Given no errors, When calling getSpecimenString, Then return specimen`() {
        // Given
        val observation =
            Observation().apply {
                setSpecimen(
                    Reference().apply {
                        setResource(
                            Specimen().apply {
                                setType(
                                    CodeableConcept().apply {
                                        addCoding(
                                            Coding().apply {
                                                setDisplay("Hello World")
                                            }
                                        )
                                    }
                                )
                            }
                        )
                    }
                )
            }

        // When
        val specimen = observation.getSpecimenString()

        // Then
        assertEquals("Hello World", specimen)
    }

    @Test
    fun `Given error, When calling getSpecimenString, Then return null`() {
        // Given
        val observation = Observation()

        // When
        val specimen = observation.getSpecimenString()

        // Then
        assertEquals(null, specimen)
    }

    @Test
    fun `Given no errors, When calling getInterpretationString, Then return interpretation`() {
        // Given
        val observation =
            Observation().apply {
                setInterpretation(
                    CodeableConcept().apply {
                        addCoding(
                            Coding().apply {
                                setSystem("http://snomed.info/sct")
                                setDisplay("Hello World")
                            }
                        )
                    }
                )
            }

        // When
        val interpretation = observation.getInterpretationString()

        // Then
        assertEquals("Hello World", interpretation)
    }

    @Test
    fun `Given error, When calling getInterpretationString, Then return interpretation`() {
        // Given
        val observation = Observation()

        // When
        val interpretation = observation.getInterpretationString()

        // Then
        assertEquals(null, interpretation)
    }

    @Test
    fun `Given no errors, When calling getCollectionDateTimeString, Then return date`() {
        // Given
        val observation =
            Observation().apply {
                setSpecimen(
                    Reference().apply {
                        setResource(
                            Specimen().apply {
                                setCollection(
                                    Specimen.SpecimenCollectionComponent().apply {
                                        setCollected(DateTimeType("2012-05-23T12:00:00+02:00"))
                                    }
                                )
                            }
                        )
                    }
                )
            }

        // When
        val date = observation.getCollectionDateTimeString()

        // Then
        assertEquals("2012-05-23T12:00:00+02:00", date)
    }

    @Test
    fun `Given error, When calling getCollectionDateTimeString, Then return date`() {
        // Given
        val observation = Observation()

        // When
        val date = observation.getCollectionDateTimeString()

        // Then
        assertEquals(null, date)
    }
}
