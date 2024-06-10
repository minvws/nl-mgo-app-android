package nl.rijksoverheid.mgo.data.api.dva

import ca.uhn.fhir.context.FhirContext
import org.hl7.fhir.dstu3.model.Bundle
import org.hl7.fhir.dstu3.model.DomainResource
import org.junit.Assert.assertEquals
import org.junit.Test

class FhirMapperTest {
    private val context = FhirContext.forDstu3()
    private val fhirMapper = FhirMapper()

    @Test
    fun `Given empty bundle, when ToDomainResources called, return empty list`() {
        // Given
        val bundle = Bundle()
        val jsonParser = context.newJsonParser()
        val bundleJson = jsonParser.encodeResourceToString(bundle)

        // When
        val domainResources = fhirMapper.toDomainResources(bundleJson)

        // Then
        assertEquals(listOf<DomainResource>(), domainResources)
    }
}
