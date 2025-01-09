package nl.rijksoverheid.mgo.data.uiSchema

interface HealthCareResourceMapper {
    fun getResources(
        fhirBundleJson: String,
        fhirVersion: FhirVersion,
    ): List<String>
}
