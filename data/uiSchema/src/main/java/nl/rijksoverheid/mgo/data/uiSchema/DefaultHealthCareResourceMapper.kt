package nl.rijksoverheid.mgo.data.uiSchema

import android.content.Context
import com.eclipsesource.v8.V8
import com.eclipsesource.v8.V8Array
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import javax.inject.Inject

internal class DefaultHealthCareResourceMapper
    @Inject
    constructor(
        @ApplicationContext private val context: Context,
    ) : HealthCareResourceMapper {
        override fun getResources(
            fhirBundleJson: String,
            fhirVersion: FhirVersion,
        ): List<String> {
            // Javascript code
            val reader1 = BufferedReader(InputStreamReader(context.assets.open("mgo-fhir-data.iife.js")))
            val jsCode = reader1.use { it.readText() }

            // Create the javascript runtime
            val runtime = V8.createV8Runtime()

            // Parse javascript
            runtime.executeVoidScript(jsCode)

            // Get bundle resources json
            val mgoFhirData = runtime.getObject("MgoFhirData")
            val getBundleResourcesJsonParameters = V8Array(runtime)
            getBundleResourcesJsonParameters.push(fhirBundleJson)
            val bundleResourcesJsonString = mgoFhirData.executeStringFunction("getBundleResourcesJson", getBundleResourcesJsonParameters)
            val bundleResourcesJsonArray = JSONArray(bundleResourcesJsonString)

            // Get ui schemas
            val resources = mutableListOf<String>()
            for (i in 0 until bundleResourcesJsonArray.length()) {
                // Get mgo resource json for requested resource type
                val bundleResourceJsonObject = bundleResourcesJsonArray.getJSONObject(i)

                val getMgoResourceJsonParameters = V8Array(runtime)
                getMgoResourceJsonParameters.push(bundleResourceJsonObject.toString())
                val options = JSONObject()
                options.put("fhirVersion", fhirVersion.toString())
                getMgoResourceJsonParameters.push(options.toString())
                val mgoResourceJson = mgoFhirData.executeStringFunction("getMgoResourceJson", getMgoResourceJsonParameters)
                resources.add(mgoResourceJson)
            }

            // Release javascript runtime
            runtime.release(false)

            return resources
        }
    }
