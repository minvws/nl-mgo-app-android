package nl.rijksoverheid.mgo.data.uiSchema

import nl.rijksoverheid.mgo.data.uiSchema.javascript.JsRuntimeRepository
import org.json.JSONObject
import javax.inject.Inject
import kotlinx.serialization.json.Json

internal class DefaultUiSchemaMapper
    @Inject
    constructor(
        private val jsRuntimeRepository: JsRuntimeRepository,
    ) : UiSchemaMapper {
        private val json = Json { ignoreUnknownKeys = true }

        override suspend fun getSummary(
            resources: List<String>,
            profiles: List<String>,
        ): List<UISchema> {
            val uiSchemas = mutableListOf<UISchema>()
            for (resourceJson in resources) {
                val resource = JSONObject(resourceJson)
                if (profiles.contains(resource.getString("profile"))) {
                    // Get ui schema json
                    val parameters = jsRuntimeRepository.createParameters(listOf(resourceJson))
                    val uiSchemaJson = jsRuntimeRepository.executeStringFunction("getUiSchemaJson", parameters)

                    // Parse ui schema json to class
                    val uiSchema = json.decodeFromString<UISchema>(uiSchemaJson)
                    uiSchemas.add(uiSchema)
                }
            }
            return uiSchemas
        }
    }
