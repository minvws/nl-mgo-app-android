package nl.rijksoverheid.mgo.data.api.dva

import ca.uhn.fhir.context.FhirContext
import org.hl7.fhir.dstu3.model.Bundle
import org.hl7.fhir.dstu3.model.Bundle.BundleEntryComponent
import org.hl7.fhir.dstu3.model.DomainResource
import org.hl7.fhir.dstu3.model.MedicationStatement
import org.junit.Assert.assertEquals
import org.junit.Test

class FhirMapperTest {
    private val context = FhirContext.forDstu3()
    private val fhirMapper = FhirMapper()
    val jsonParser = context.newJsonParser()

    @Test
    fun `Given empty bundle, when ToDomainResources called, return empty list`() {
        // Given
        val bundle = Bundle()
        val bundleJson = jsonParser.encodeResourceToString(bundle)

        // When
        val domainResources = fhirMapper.toDomainResources(bundleJson)

        // Then
        assertEquals(listOf<DomainResource>(), domainResources)
    }

    @Test
    fun `Given bundle with medication statement, when ToDomainResources called, return domain resource`() {
        // Given
        val bundle =
            Bundle().apply {
                val entry =
                    BundleEntryComponent().apply {
                        val medicationStatement =
                            MedicationStatement().apply {
                                setId("123")
                            }
                        setResource(medicationStatement)
                    }
                addEntry(entry)
            }
        val bundleJson = jsonParser.encodeResourceToString(bundle)

        // When
        val domainResources = fhirMapper.toDomainResources(bundleJson)

        // Then
        assertEquals(1, domainResources.size)
    }
}
