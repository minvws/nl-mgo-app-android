package nl.rijksoverheid.mgo.data.uiSchema

interface UiSchemaMapper {
    fun getUiSchema(
        fhirBundleJson: String,
        resourceType: ResourceType,
    ): List<UISchema>
}
