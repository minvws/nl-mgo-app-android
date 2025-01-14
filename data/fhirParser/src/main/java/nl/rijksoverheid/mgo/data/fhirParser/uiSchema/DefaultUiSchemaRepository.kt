package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceJson
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceRepository
import nl.rijksoverheid.mgo.data.fhirParser.shared.UISchema
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
         * @param mgoResource The mgo resource created in [MgoResourceRepository].
         */
        override suspend fun getSummary(mgoResource: MgoResourceJson): UISchema {
            return getUiSchemas(
                mgoResource = mgoResource,
                jsFunctionName = "getSummaryUiSchemaJson",
            )
        }

        /**
         * Get all health care data to display for a user.
         * @param mgoResource The mgo resource created in [MgoResourceRepository].
         */
        override suspend fun getDetail(mgoResource: MgoResourceJson): UISchema {
            return getUiSchemas(
                mgoResource = mgoResource,
                jsFunctionName = "getUiSchemaJson",
            )
        }

        private suspend fun getUiSchemas(
            mgoResource: MgoResourceJson,
            jsFunctionName: String,
        ): UISchema {
            val uiSchemaJson = jsRuntimeRepository.executeStringFunction(jsFunctionName, listOf(mgoResource))
            return json.decodeFromString<UISchema>(uiSchemaJson)
        }
    }
