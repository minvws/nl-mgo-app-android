package nl.rijksoverheid.mgo.data.uiSchema

class TestUiSchemaMapper(private val result: List<UISchema>) : UiSchemaMapper {
    override fun getUiSchema(
        fhirBundleJson: String,
        fhirVersion: FhirVersion,
        profiles: List<String>,
    ): List<UISchema> {
        return result
    }
}
