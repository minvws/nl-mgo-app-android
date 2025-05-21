package nl.rijksoverheid.mgo.data.fhirParser.uiSchema

import kotlinx.serialization.json.Json
import nl.rijksoverheid.mgo.data.fhirParser.js.JsRuntimeRepository
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResource
import nl.rijksoverheid.mgo.data.fhirParser.mgoResource.MgoResourceMapper
import nl.rijksoverheid.mgo.data.fhirParser.models.HealthUiSchema
import nl.rijksoverheid.mgo.framework.util.base64.Base64Util
import javax.inject.Inject

/**
 * Creates [HealthUiSchema] based on a [MgoResource].
 *
 * @param jsRuntimeRepository The [JsRuntimeRepository] to handle javascript.
 * @param base64Util The [Base64Util] to handle base64 decoding.
 */
internal class DefaultUiSchemaMapper
  @Inject
  constructor(
    private val jsRuntimeRepository: JsRuntimeRepository,
    private val base64Util: Base64Util,
  ) : UiSchemaMapper {
    private val json =
      Json {
        ignoreUnknownKeys = true
      }

    /**
     * Retrieves a summarized version of the most important healthcare data from an [MgoResource].
     *
     * @param mgoResource The [MgoResource] created in [MgoResourceMapper].
     * @return [HealthUiSchema].
     */
    override suspend fun getSummary(mgoResource: MgoResource): HealthUiSchema {
      return getUiSchemas(
        mgoResource = mgoResource,
        jsFunctionName = "getSummaryJson",
      )
    }

    /**
     * Retrieves the complete set of healthcare data from an [MgoResource].
     *
     * @param mgoResource The [MgoResource] created in [MgoResourceMapper].
     * @return [HealthUiSchema].
     */
    override suspend fun getDetail(mgoResource: MgoResource): HealthUiSchema {
      return getUiSchemas(
        mgoResource = mgoResource,
        jsFunctionName = "getDetailsJson",
      )
    }

    /**
     * Executes a JavaScript function to retrieve a UI schema based on an [MgoResource].
     *
     * The function extracts the Base64-encoded JSON data from [mgoResource], decodes it,
     * and passes it as an argument to the specified JavaScript function in the V8 runtime.
     * The returned JSON string is then deserialized into a [HealthUiSchema] object.
     *
     * @param mgoResource The FHIR resource containing Base64-encoded healthcare data.
     * @param jsFunctionName The name of the JavaScript function to execute (e.g., "getSummaryJson" or "getDetailsJson").
     * @return [HealthUiSchema].
     */
    private suspend fun getUiSchemas(
      mgoResource: MgoResource,
      jsFunctionName: String,
    ): HealthUiSchema {
      val mgoResourceJson = base64Util.decode(mgoResource.jsonBase64)
      val uiSchemaJson =
        jsRuntimeRepository.executeStringFunction(
          jsFunctionName,
          listOf(mgoResourceJson),
        )
      return json.decodeFromString<HealthUiSchema>(uiSchemaJson)
    }
  }
