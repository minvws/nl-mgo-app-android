package nl.rijksoverheid.mgo.data.uiSchema

class TestUiSchemaMapper(private val result: List<UISchema>) : UiSchemaMapper {
    override fun getUiSchema(
        fhirBundleJson: String,
        resourceType: ResourceType,
    ): List<UISchema> {
        return result
    }
}
