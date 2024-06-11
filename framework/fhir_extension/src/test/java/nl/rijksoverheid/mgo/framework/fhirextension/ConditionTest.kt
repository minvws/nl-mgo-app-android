package nl.rijksoverheid.mgo.framework.fhirextension

import org.hl7.fhir.dstu3.model.Annotation
import org.hl7.fhir.dstu3.model.CodeableConcept
import org.hl7.fhir.dstu3.model.Coding
import org.hl7.fhir.dstu3.model.Condition
import org.hl7.fhir.dstu3.model.DateTimeType
import org.hl7.fhir.dstu3.model.Extension
import org.hl7.fhir.dstu3.model.StringType
import org.junit.Assert.assertEquals
import org.junit.Test

class ConditionTest {
    @Test
    fun `Given no errors, When calling getTitleString, Then return title`() {
        // Given
        val condition =
            Condition().apply {
                val code =
                    CodeableConcept().apply {
                        val coding =
                            Coding().apply {
                                display = "Hello World"
                            }
                        setCoding(listOf(coding))
                    }
                setCode(code)
            }

        // When
        val title = condition.getTitleString()

        // Then
        assertEquals("Hello World", title)
    }

    @Test
    fun `Given error, When calling getTitleString, Then return null`() {
        // Given
        val condition = Condition()

        // When
        val title = condition.getTitleString()

        // Then
        assertEquals(null, title)
    }

    @Test
    fun `Given no notes, When calling getCommentString, Then return null`() {
        // Given
        val condition =
            Condition().apply {
                note = listOf()
            }

        // When
        val comment = condition.getCommentString()

        // Then
        assertEquals(null, comment)
    }

    @Test
    fun `Given notes, When calling getCommentString, Then return comments`() {
        // Given
        val condition =
            Condition().apply {
                note =
                    listOf(
                        Annotation(StringType("Hello World 1")),
                        Annotation(StringType("Hello World 2")),
                    )
            }

        // When
        val comment = condition.getCommentString()

        // Then
        assertEquals("Hello World 1, Hello World 2", comment)
    }

    @Test
    fun `Given no errors, When calling getClinicalStatusString, Then return clinical status`() {
        // Given
        val condition =
            Condition().apply {
                setClinicalStatus(Condition.ConditionClinicalStatus.ACTIVE)
            }

        // When
        val clinicalStatus = condition.getClinicalStatusString()

        // Then
        assertEquals("active", clinicalStatus)
    }

    @Test
    fun `Given error, When calling getClinicalStatusString, Then return null`() {
        // Given
        val condition = Condition()

        // When
        val clinicalStatus = condition.getClinicalStatusString()

        // Then
        assertEquals(null, clinicalStatus)
    }

    @Test
    fun `Given no category, When calling getCategoryString, Then return null`() {
        // Given
        val condition =
            Condition().apply {
                category = listOf()
            }

        // When
        val category = condition.getCategoryString()

        // Then
        assertEquals(null, category)
    }

    @Test
    fun `Given category, When calling getCategoryString, Then return null`() {
        // Given
        val condition =
            Condition().apply {
                category =
                    listOf(
                        CodeableConcept().apply {
                            val coding =
                                Coding().apply {
                                    setDisplay("Hello World 1")
                                }
                            setCoding(listOf(coding))
                        },
                        CodeableConcept().apply {
                            val coding =
                                Coding().apply {
                                    setDisplay("Hello World 2")
                                }
                            setCoding(listOf(coding))
                        },
                    )
            }

        // When
        val category = condition.getCategoryString()

        // Then
        assertEquals("Hello World 1, Hello World 2", category)
    }

    @Test
    fun `Given no errors, When calling getStartDateString, Then return start date`() {
        // Given
        val condition =
            Condition().apply {
                setOnset(DateTimeType("2018-06-28"))
            }

        // When
        val startDate = condition.getStartDateString()

        // Then
        assertEquals("2018-06-28", startDate)
    }

    @Test
    fun `Given errors, When calling getStartDateString, Then return null`() {
        // Given
        val condition =
            Condition().apply {
                setOnset(null)
            }

        // When
        val startDate = condition.getStartDateString()

        // Then
        assertEquals(null, startDate)
    }

    @Test
    fun `Given no errors, When calling getEndDateString, Then return end date`() {
        // Given
        val condition =
            Condition().apply {
                setAbatement(DateTimeType("2018-06-28"))
            }

        // When
        val endDate = condition.getEndDateString()

        // Then
        assertEquals("2018-06-28", endDate)
    }

    @Test
    fun `Given errors, When calling getEndDateString, Then return null`() {
        // Given
        val condition =
            Condition().apply {
                setAbatement(null)
            }

        // When
        val endDate = condition.getEndDateString()

        // Then
        assertEquals(null, endDate)
    }

    @Test
    fun `Given empty body site, When calling getBodyLocationString, Then return null`() {
        // Given
        val condition =
            Condition().apply {
                setBodySite(listOf())
            }

        // When
        val bodyLocation = condition.getBodyLocationString()

        // Then
        assertEquals(null, bodyLocation)
    }

    @Test
    fun `Given error, When calling getBodyLocationString, Then return null`() {
        // Given
        val condition =
            Condition().apply {
                setBodySite(
                    listOf(
                        CodeableConcept(),
                    ),
                )
            }

        // When
        val bodyLocation = condition.getBodyLocationString()

        // Then
        assertEquals(null, bodyLocation)
    }

    @Test
    fun `Given coding, When calling getBodyLocationString, Then return first part`() {
        // Given
        val condition =
            Condition().apply {
                setBodySite(
                    listOf(
                        CodeableConcept().apply {
                            val coding =
                                Coding().apply {
                                    setDisplay("Hello World")
                                }
                            setCoding(listOf(coding))
                        },
                    ),
                )
            }

        // When
        val bodyLocation = condition.getBodyLocationString()

        // Then
        assertEquals("Hello World", bodyLocation)
    }

    @Test
    fun `Given coding and extension, When calling getBodyLocationString, Then return first and second part`() {
        // Given
        val condition =
            Condition().apply {
                setBodySite(
                    listOf(
                        CodeableConcept().apply {
                            val coding =
                                Coding().apply {
                                    setDisplay("Hello World 1")
                                }
                            setCoding(listOf(coding))
                            val extension =
                                listOf(
                                    Extension().apply {
                                        val codeableConcept =
                                            CodeableConcept().apply {
                                                val innerCoding =
                                                    Coding().apply {
                                                        setDisplay("Hello World 2")
                                                    }
                                                setCoding(listOf(innerCoding))
                                            }
                                        setValue(codeableConcept)
                                    },
                                )
                            setExtension(extension)
                        },
                    ),
                )
            }

        // When
        val bodyLocation = condition.getBodyLocationString()

        // Then
        assertEquals("Hello World 1, Hello World 2", bodyLocation)
    }
}
