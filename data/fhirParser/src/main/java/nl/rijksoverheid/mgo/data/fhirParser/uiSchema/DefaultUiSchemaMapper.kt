package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceMapper
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema
import nl.rijksoverheid.mgo.framework.util.base64.Base64Util
import javax.inject.Inject
import kotlinx.serialization.json.Json

/**
 * Creates [UISchema] based on [MgoResource].
 */
internal class DefaultUiSchemaMapper
    @Inject
    constructor(private val jsRuntimeRepository: JsRuntimeRepository, private val base64Util: Base64Util) : UiSchemaMapper {
        private val json =
            Json {
                ignoreUnknownKeys = true
            }

        /**
         * Get a summary of most important health care data to display for a user.
         * @param mgoResource The mgo resource created in [MgoResourceMapper].
         */
        override suspend fun getSummary(mgoResource: MgoResource): HealthUiSchema {
            return getUiSchemas(
                mgoResource = mgoResource,
                jsFunctionName = "getSummaryJson",
            )
        }

        /**
         * Get all health care data to display for a user.
         * @param mgoResource The mgo resource created in [MgoResourceMapper].
         */
        override suspend fun getDetail(mgoResource: MgoResource): HealthUiSchema {
            return getUiSchemas(
                mgoResource = mgoResource,
                jsFunctionName = "getDetailsJson",
            )
        }

        private suspend fun getUiSchemas(
            mgoResource: MgoResource,
            jsFunctionName: String,
        ): HealthUiSchema {
            val mgoResourceJson = base64Util.decode(mgoResource.jsonBase64)
            val uiSchemaJson = jsRuntimeRepository.executeStringFunction(jsFunctionName, listOf(mgoResourceJson))
            return json.decodeFromString<HealthUiSchema>(uiSchemaJson)
        }
    }
