package nl.rijksoverheid.mgo.data.uiSchema.repository

import nl.rijksoverheid.mgo.data.uiSchema.UISchema
import javax.inject.Singleton

@Singleton
internal class DefaultUiSchemaRepository : UiSchemaRepository {
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
