package nl.rijksoverheid.mgo.data.uiSchema

import nl.rijksoverheid.mgo.data.uiSchema.repository.UiSchemaCacheKey
import nl.rijksoverheid.mgo.data.uiSchema.repository.UiSchemaRepository

class TestUiSchemaRepository : UiSchemaRepository {
    private val cache: MutableMap<UiSchemaCacheKey, List<UISchema>> = mutableMapOf()

    override fun store(
        key: UiSchemaCacheKey,
        uiSchemaList: List<UISchema>,
    ) {
        cache[key] = uiSchemaList
    }

    override fun get(key: UiSchemaCacheKey): List<UISchema>? {
        return cache[key]
    }
}
