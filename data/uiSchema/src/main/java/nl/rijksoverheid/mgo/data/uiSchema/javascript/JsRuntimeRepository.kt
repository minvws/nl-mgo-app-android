package nl.rijksoverheid.mgo.data.uiSchema.javascript

import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array

interface JsRuntimeRepository {
    suspend fun load()

    suspend fun get(): V8

    suspend fun createParameters(parameters: List<String>): V8Array

    suspend fun executeStringFunction(
        name: String,
        parameters: V8Array,
    ): String
}
