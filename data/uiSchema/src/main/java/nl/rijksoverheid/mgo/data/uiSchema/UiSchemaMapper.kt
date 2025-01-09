package nl.rijksoverheid.mgo.data.uiSchema

interface UiSchemaMapper {
    suspend fun getSummary(
        resources: List<String>,
        profiles: List<String>,
    ): List<UISchema>
}
