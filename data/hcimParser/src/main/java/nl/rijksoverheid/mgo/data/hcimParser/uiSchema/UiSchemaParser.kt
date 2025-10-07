package nl.rijksoverheid.mgo.data.hcimParser.uiSchema

import android.annotation.SuppressLint
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import nl.rijksoverheid.mgo.data.hcimParser.javascript.JsEngineRepository
import nl.rijksoverheid.mgo.data.hcimParser.uiSchema.models.HealthUiSchema
import javax.inject.Inject

class UiSchemaParser
  @Inject
  constructor(
    private val jsEngineRepository: JsEngineRepository,
  ) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun getSummary(
      mgoResourceJson: String,
      organizationName: String,
    ): HealthUiSchema = getUiSchema(functionName = "getSummaryJson", mgoResourceJson = mgoResourceJson, organizationName = organizationName)

    suspend fun getDetails(
      mgoResourceJson: String,
      organizationName: String,
    ): HealthUiSchema = getUiSchema(functionName = "getDetailsJson", mgoResourceJson = mgoResourceJson, organizationName = organizationName)

    private suspend fun getUiSchema(
      functionName: String,
      mgoResourceJson: String,
      organizationName: String,
    ): HealthUiSchema {
      // Map organizationName to expected json
      val organizationJson = json.encodeToString(OrganizationJson(organization = OrganizationJson.Organization(organizationName)))

      // Get output of javascript call
      val uiSchemaJsonOutput =
        jsEngineRepository.executeStringFunction(
          functionName = functionName,
          parameters = listOf(mgoResourceJson, organizationJson),
        )

      // Return ui schema from json
      return json.decodeFromString<HealthUiSchema>(uiSchemaJsonOutput)
    }
  }

/**
 * Wrapper class to send our organization name to the javascript function in the correct json format.
 */
@SuppressLint("UnsafeOptInUsageError")
@Serializable
internal data class OrganizationJson(
  val organization: Organization,
) {
  @Serializable
  data class Organization(
    val name: String,
  )
}
