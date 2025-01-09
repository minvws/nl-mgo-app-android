package nl.rijksoverheid.mgo.data.uiSchema

import nl.rijksoverheid.mgo.data.uiSchema.javascript.JsRuntimeRepository
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

internal class DefaultHealthCareResourceMapper
    @Inject
    constructor(
        private val jsRuntimeRepository: JsRuntimeRepository
    ) : HealthCareResourceMapper {
        override suspend fun getResources(
            fhirBundleJson: String,
            fhirVersion: FhirVersion,
        ): List<String> {
            val getBundleResourcesJsonParameters = jsRuntimeRepository.createParameters(listOf(fhirBundleJson))
            val bundleResourcesJsonString = jsRuntimeRepository.executeStringFunction("getBundleResourcesJson", getBundleResourcesJsonParameters)
            val bundleResourcesJsonArray = JSONArray(bundleResourcesJsonString)

            // Get ui schemas
            val resources = mutableListOf<String>()
            for (i in 0 until bundleResourcesJsonArray.length()) {
                // Get mgo resource json for requested resource type
                val bundleResourceJsonObject = bundleResourcesJsonArray.getJSONObject(i)

                val getMgoResourceJsonParameters = jsRuntimeRepository.createParameters(
                    listOf(
                        bundleResourceJsonObject.toString(),
                        JSONObject().apply { put("fhirVersion", fhirVersion.toString()) }.toString()
                    )
                )

                val mgoResourceJson = jsRuntimeRepository.executeStringFunction("getMgoResourceJson", getMgoResourceJsonParameters)
                resources.add(mgoResourceJson)
            }

            return resources
        }
    }
