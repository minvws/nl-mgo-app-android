package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceJson
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceRepository
import nl.rijksoverheid.mgo.data.fhirParser.shared.UISchema
import org.json.JSONObject
import javax.inject.Inject
import kotlinx.serialization.json.Json

/**
 * Creates [UISchema] based on [MgoResourceJson].
 */
internal class DefaultUiSchemaRepository
    @Inject
    constructor(private val jsRuntimeRepository: JsRuntimeRepository) : UiSchemaRepository {
        private val json = Json { ignoreUnknownKeys = true }

        /**
         * Get a summary of most important health care data to display for a user.
         * @param mgoResources The mgo resources created in [MgoResourceRepository].
         * @param profiles Each [MgoResourceJson] has a profile field. If this field exists in this array, it will be parsed to a UISchema.
         */
        override suspend fun getSummary(
            mgoResources: List<MgoResourceJson>,
            profiles: List<String>,
        ): List<UISchema> {
            return getUiSchemas(
                mgoResources = mgoResources,
                profiles = profiles,
                jsFunctionName = "getSummaryUiSchemaJson",
            )
        }

        /**
         * Get all health care data to display for a user.
         * @param mgoResources The mgo resources created in [MgoResourceRepository].
         * @param profiles Each [MgoResourceJson] has a profile field. If this field exists in this array, it will be parsed to a UISchema.
         */
        override suspend fun getDetail(
            mgoResources: List<MgoResourceJson>,
            profiles: List<String>,
        ): List<UISchema> {
            return getUiSchemas(
                mgoResources = mgoResources,
                profiles = profiles,
                jsFunctionName = "getUiSchemaJson",
            )
        }

        private suspend fun getUiSchemas(
            mgoResources: List<MgoResourceJson>,
            profiles: List<String>,
            jsFunctionName: String,
        ): List<UISchema> {
            return mgoResources.mapNotNull { mgoResource ->
                val mgoResourceJsonObject = JSONObject(mgoResource)
                if (profiles.contains(mgoResourceJsonObject.getString("profile"))) {
                    val uiSchemaJson = jsRuntimeRepository.executeStringFunction(jsFunctionName, listOf(mgoResource))
                    json.decodeFromString<UISchema>(uiSchemaJson)
                } else {
                    null
                }
            }
        }
    }
