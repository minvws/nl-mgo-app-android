package nl.rijksoverheid.mgo.data.uiSchema

interface UiSchemaMapper {
    fun getUiSchema(
        fhirBundleJson: String,
        profiles: List<String>,
    ): List<UISchema>
}
