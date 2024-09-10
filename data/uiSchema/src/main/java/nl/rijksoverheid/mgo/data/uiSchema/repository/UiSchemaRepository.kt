package nl.rijksoverheid.mgo.data.uiSchema.repository

import nl.rijksoverheid.mgo.data.uiSchema.UISchema

interface UiSchemaRepository {
    fun store(
        key: UiSchemaCacheKey,
        uiSchemaList: List<UISchema>,
    )

    fun get(key: UiSchemaCacheKey): List<UISchema>?
}
