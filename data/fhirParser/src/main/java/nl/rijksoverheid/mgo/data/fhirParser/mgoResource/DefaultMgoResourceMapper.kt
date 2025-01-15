package nl.rijksoverheid.mgo.data.fhirParser.mgoResource

import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.UiSchemaMapper
import org.json.JSONArray
import org.json.JSONObject
import javax.inject.Inject

/**
 * Creates [MgoResourceJson] based on a FHIR Response (https://hl7.org/fhir/).
 */
internal class DefaultMgoResourceMapper
    @Inject
    constructor(private val jsRuntimeRepository: JsRuntimeRepository) :
    MgoResourceMapper {
        /**
         * Parses the fhir response, and returns a list of [MgoResourceJson] that can then be used in [UiSchemaMapper]
         */
        override suspend fun get(
            fhirBundleJson: String,
            fhirVersion: FhirVersion,
        ): List<MgoResourceJson> {
            // Get bundles as json array
            val bundleResources =
                JSONArray(
                    jsRuntimeRepository.executeStringFunction(
                        "getBundleResourcesJson",
                        listOf(fhirBundleJson),
                    ),
                )

            // Convert bundles to mgo resource json
            val mgoResources = mutableListOf<MgoResourceJson>()
            for (i in 0 until bundleResources.length()) {
                val bundleResource = bundleResources.getJSONObject(i)
                val mgoResource =
                    jsRuntimeRepository.executeStringFunction(
                        "getMgoResourceJson",
                        listOf(
                            bundleResource.toString(),
                            JSONObject().apply { put("fhirVersion", fhirVersion.toString()) }.toString(),
                        ),
                    )
                mgoResources.add(mgoResource)
            }
            return mgoResources
        }
    }
