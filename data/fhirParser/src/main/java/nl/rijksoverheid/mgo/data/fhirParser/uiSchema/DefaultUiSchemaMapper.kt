package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceMapper
import nl.rijksoverheid.mgo.data.fhirParser.shared.UISchema
import javax.inject.Inject
import kotlinx.serialization.json.Json

/**
 * Creates [UISchema] based on [MgoResourceJson].
 */
internal class DefaultUiSchemaMapper
    @Inject
    constructor(private val jsRuntimeRepository: JsRuntimeRepository) : UiSchemaMapper {
        private val json = Json { ignoreUnknownKeys = true }

        /**
         * Get a summary of most important health care data to display for a user.
         * @param mgoResource The mgo resource created in [MgoResourceMapper].
         */
        override suspend fun getSummary(mgoResource: MgoResource): UISchema {
            return getUiSchemas(
                mgoResource = mgoResource,
                jsFunctionName = "getSummaryUiSchemaJson",
            )
        }

        /**
         * Get all health care data to display for a user.
         * @param mgoResource The mgo resource created in [MgoResourceMapper].
         */
        override suspend fun getDetail(mgoResource: MgoResource): UISchema {
            return getUiSchemas(
                mgoResource = mgoResource,
                jsFunctionName = "getUiSchemaJson",
            )
        }

        private suspend fun getUiSchemas(
            mgoResource: MgoResource,
            jsFunctionName: String,
        ): UISchema {
            val uiSchemaJson = jsRuntimeRepository.executeStringFunction(jsFunctionName, listOf(mgoResource.json))
            return json.decodeFromString<UISchema>(uiSchemaJson)
        }
    }
