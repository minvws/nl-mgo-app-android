package nl.rijksoverheid.mgo.data.uiSchema

import android.content.Context
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONArray
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

// TODO: Because the V8 library is written in C++, objects needs to be released properly.
internal class DefaultUiSchemaMapper
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : UiSchemaMapper {
        // Create javascript runtime
        private val runtime = V8.createV8Runtime()

        override fun getUiSchema(fhirBundleJson: String): Result<List<UISchema>> {
            // Javascript code
            val reader1 = BufferedReader(InputStreamReader(context.assets.open("mgo-fhir-data.js")))
            val jsCode = reader1.use { it.readText() }

            try {
                // Parse javascript
                runtime.executeVoidScript(jsCode)

                // Get bundle resources json
                val getBundleResourcesJsonParameters = V8Array(runtime)
                getBundleResourcesJsonParameters.push(fhirBundleJson)
                val bundleResourcesJsonString = runtime.executeStringFunction("getBundleResourcesJson", getBundleResourcesJsonParameters)
                val bundleResourcesJsonArray = JSONArray(bundleResourcesJsonString)

                // Get ui schemas
                val uiSchemas = mutableListOf<UISchema>()
                for (i in 0 until bundleResourcesJsonArray.length()) {
                    // Get mgo resource json
                    val bundleResourceJsonObject = bundleResourcesJsonArray.getJSONObject(i)
                    val getMgoResourceJsonParameters = V8Array(runtime)
                    getMgoResourceJsonParameters.push(bundleResourceJsonObject.toString())
                    val mgoResourceJson = runtime.executeStringFunction("getMgoResourceJson", getMgoResourceJsonParameters)

                    // Get ui schema json
                    val getUiSchemaJsonParameters = V8Array(runtime)
                    getUiSchemaJsonParameters.push(mgoResourceJson)
                    val uiSchemaJson = runtime.executeStringFunction("getUiSchemaJson", getUiSchemaJsonParameters)

                    // Parse ui schema json to class
                    val uiSchema = UISchema.fromJson(uiSchemaJson)
                    uiSchemas.add(uiSchema)
                }

                return Result.success(uiSchemas)
            } catch (e: Exception) {
                return Result.failure(e)
            }
        }
    }
