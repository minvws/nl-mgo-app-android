package nl.rijksoverheid.mgo.data.uiSchema

interface UiSchemaMapper {
    fun getSummary(
        resources: List<String>,
        profiles: List<String>,
    ): List<UISchema>

    fun getDetail(
        resources: List<String>,
        profiles: List<String>,
    ): List<UISchema>
}
