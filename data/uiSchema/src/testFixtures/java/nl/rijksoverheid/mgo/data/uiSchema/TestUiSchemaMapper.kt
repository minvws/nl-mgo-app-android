package nl.rijksoverheid.mgo.data.uiSchema

class TestUiSchemaMapper(private val result: List<UISchema>) : UiSchemaMapper {
    override fun getUiSchema(
        fhirBundleJson: String,
        profile: String,
    ): List<UISchema> {
        return result
    }
}
