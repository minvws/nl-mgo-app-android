package nl.rijksoverheid.mgo.data.fhirParser.mgoResource

interface MgoResourceRepository {
    suspend fun get(
        fhirBundleJson: String,
        fhirVersion: FhirVersion,
    ): List<MgoResourceJson>
}
