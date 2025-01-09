package nl.rijksoverheid.mgo.data.uiSchema

interface HealthCareResourceMapper {
    suspend fun getResources(
        fhirBundleJson: String,
        fhirVersion: FhirVersion,
    ): List<String>
}
