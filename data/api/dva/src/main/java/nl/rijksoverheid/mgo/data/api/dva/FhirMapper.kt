package nl.rijksoverheid.mgo.data.api.dva

import ca.uhn.fhir.context.FhirContext
import org.hl7.fhir.dstu3.model.Bundle
import org.hl7.fhir.dstu3.model.DomainResource

class FhirMapper {
    private val context = FhirContext.forDstu3()
    private val parser = context.newJsonParser()

    /**
     * Maps a FHIR response to a list of [DomainResource].
     */
    fun toDomainResources(fhirResponseJson: String): List<DomainResource> {
        // Parse the bundle
        val bundle = parser.parseResource(Bundle::class.java, fhirResponseJson)

        // Get the domain resources from the bundle entries
        val domainResources =
            bundle.entry.map { entryComponent ->
                entryComponent.resource as DomainResource
            }

        return domainResources
    }
}
