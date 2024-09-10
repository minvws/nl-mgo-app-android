package nl.rijksoverheid.mgo.data.uiSchema.repository

data class UiSchemaCacheKey(
    val organizationId: String,
    val category: UiSchemaCacheCategory,
)
