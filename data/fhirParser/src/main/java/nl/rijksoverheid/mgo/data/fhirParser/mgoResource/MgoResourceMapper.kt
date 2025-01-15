package nl.rijksoverheid.mgo.data.fhirParser.mgoResource

interface MgoResourceMapper {
    suspend fun get(
        fhirBundleJson: String,
        fhirVersion: FhirVersion,
    ): List<MgoResourceJson>
}
