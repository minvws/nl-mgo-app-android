package nl.rijksoverheid.mgo.data.fhirParser.js

interface JsRuntimeRepository {
    suspend fun load()

    suspend fun executeStringFunction(
        name: String,
        parameters: List<String>,
    ): String
}
