package nl.rijksoverheid.mgo.data.uiSchema

interface UiSchemaMapper {
    fun getUiSchema(
        fhirBundleJson: String,
        fhirVersion: FhirVersion,
        profiles: List<String>,
    ): List<UISchema>
}
