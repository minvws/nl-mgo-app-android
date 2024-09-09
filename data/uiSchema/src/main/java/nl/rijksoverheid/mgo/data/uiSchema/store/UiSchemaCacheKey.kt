package nl.rijksoverheid.mgo.data.uiSchema.store

data class UiSchemaCacheKey(
    val providerId: String,
    val category: UiSchemaCacheCategory,
)
