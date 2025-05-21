package nl.rijksoverheid.mgo.data.fhirParser.mgoResource

class TestMgoResourceMapper : MgoResourceMapper {
  override suspend fun get(
    fhirBundleJson: String,
    fhirVersion: FhirVersion,
  ): List<MgoResource> {
    return listOf(TEST_MGO_RESOURCE)
  }
}
