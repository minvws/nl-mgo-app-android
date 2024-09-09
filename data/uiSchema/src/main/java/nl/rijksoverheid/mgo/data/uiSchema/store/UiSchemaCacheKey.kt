package nl.rijksoverheid.mgo.data.uiSchema.store

data class UiSchemaCacheKey(
    val organizationId: String,
    val category: UiSchemaCacheCategory,
)
