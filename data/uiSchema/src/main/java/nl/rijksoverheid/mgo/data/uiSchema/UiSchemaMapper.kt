package nl.rijksoverheid.mgo.data.uiSchema

interface UiSchemaMapper {
    fun getUiSchema(fhirBundleJson: String): Result<List<UISchema>>
}
