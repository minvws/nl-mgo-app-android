package nl.rijksoverheid.mgo.data.fhirParser.mgoResource

interface MgoResourceRepository {
    suspend fun get(
        fhirBundleJson: String,
        fhirVersion: FhirVersion,
    ): List<MgoResourceJson>

    suspend fun filter(
        resources: List<MgoResourceJson>,
        profiles: List<String>,
    ): List<MgoResourceJson>
}
