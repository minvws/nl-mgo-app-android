package nl.rijksoverheid.mgo.data.fhirParser.mgoResource

import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.UiSchemaMapper
import nl.rijksoverheid.mgo.framework.util.base64.Base64Util
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

/**
 * Creates [MgoResource] based on a FHIR Response (https://hl7.org/fhir/).
 */
internal class DefaultMgoResourceMapper
    @Inject
    constructor(private val jsRuntimeRepository: JsRuntimeRepository, private val base64Util: Base64Util) :
    MgoResourceMapper {
        /**
         * Parses the fhir response, and returns a list of [MgoResource] that can then be used in [UiSchemaMapper]
         */
        override suspend fun get(
            fhirBundleJson: String,
            fhirVersion: FhirVersion,
        ): List<MgoResource> {
            // Get bundles as json array
            val bundleResources =
                JSONArray(
                    jsRuntimeRepository.executeStringFunction(
                        "getBundleResourcesJson",
                        listOf(fhirBundleJson),
                    ),
                )

            // Convert bundles to mgo resource json
            val mgoResources = mutableListOf<MgoResource>()
            for (i in 0 until bundleResources.length()) {
                val bundleResource = bundleResources.getJSONObject(i)
                val mgoResourceJsonString =
                    jsRuntimeRepository.executeStringFunction(
                        "getMgoResourceJson",
                        listOf(
                            bundleResource.toString(),
                            JSONObject().apply { put("fhirVersion", fhirVersion.toString()) }.toString(),
                        ),
                    )
                val mgoResource = mgoResourceJsonString.toMgoResource(base64Util.encode(mgoResourceJsonString))
                mgoResources.add(mgoResource)
            }
            return mgoResources
        }
    }
