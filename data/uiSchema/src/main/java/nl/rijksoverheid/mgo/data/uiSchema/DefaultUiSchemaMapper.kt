package nl.rijksoverheid.mgo.data.uiSchema

import android.content.Context
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject
import kotlinx.serialization.json.Json

internal class DefaultUiSchemaMapper
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : UiSchemaMapper {
        private val json = Json { ignoreUnknownKeys = true }

        override fun getSummary(
            resources: List<String>,
            profiles: List<String>,
        ): List<UISchema> {
            // Javascript code
            val reader1 = BufferedReader(InputStreamReader(context.assets.open("mgo-fhir-data.iife.js")))
            val jsCode = reader1.use { it.readText() }

            // Create the javascript runtime
            val runtime = V8.createV8Runtime()

            // Parse javascript
            runtime.executeVoidScript(jsCode)

            val mgoFhirData = runtime.getObject("MgoFhirData")

            val uiSchemas = mutableListOf<UISchema>()

            for (resourceJson in resources) {
                val resource = JSONObject(resourceJson)
                if (profiles.contains(resource.getString("profile"))) {
                    // Get ui schema json
                    val getUiSchemaJsonParameters = V8Array(runtime)
                    getUiSchemaJsonParameters.push(resourceJson)
                    val uiSchemaJson = mgoFhirData.executeStringFunction("getUiSchemaJson", getUiSchemaJsonParameters)

                    // Parse ui schema json to class
                    val uiSchema = json.decodeFromString<UISchema>(uiSchemaJson)
                    uiSchemas.add(uiSchema)
                }
            }

            // Release javascript runtime
            runtime.release(false)

            return uiSchemas
        }

        override fun getDetail(
            resources: List<String>,
            profiles: List<String>,
        ): List<UISchema> {
            // Javascript code
            val reader1 = BufferedReader(InputStreamReader(context.assets.open("mgo-fhir-data.iife.js")))
            val jsCode = reader1.use { it.readText() }

            // Create the javascript runtime
            val runtime = V8.createV8Runtime()

            val mgoFhirData = runtime.getObject("MgoFhirData")

            // Parse javascript
            runtime.executeVoidScript(jsCode)

            val uiSchemas = mutableListOf<UISchema>()

            for (resourceJson in resources) {
                val resource = JSONObject(resourceJson)
                if (profiles.contains(resource.getString("profile"))) {
                    // Get ui schema json
                    val getUiSchemaJsonParameters = V8Array(runtime)
                    getUiSchemaJsonParameters.push(resourceJson)
                    val uiSchemaJson = mgoFhirData.executeStringFunction("getUiSchemaJson", getUiSchemaJsonParameters)

                    // Parse ui schema json to class
                    val uiSchema = json.decodeFromString<UISchema>(uiSchemaJson)
                    uiSchemas.add(uiSchema)
                }
            }

            // Release javascript runtime
            runtime.release(false)

            return uiSchemas
        }
    }
