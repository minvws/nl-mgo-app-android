package nl.rijksoverheid.mgo.data.hcimParser.mgoResource

import kotlinx.serialization.json.Json
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive
import nl.rijksoverheid.mgo.data.hcimParser.javascript.JsEngineRepository
import nl.rijksoverheid.mgo.framework.fhir.FhirVersion
import javax.inject.Inject

class MgoResourceParser
  @Inject
  constructor(
    private val jsEngineRepository: JsEngineRepository,
  ) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend operator fun invoke(
      fhirResponse: String,
      fhirVersion: FhirVersion,
    ): List<MgoResource> {
      // Get output of javascript call from getBundleResourcesJson
      val getBundleResourcesJsonOutput =
        jsEngineRepository.executeStringFunction(
          functionName = "getBundleResourcesJson",
          parameters = listOf(fhirResponse),
        )

      // Create json array from output
      val bundleJsonArray = json.parseToJsonElement(getBundleResourcesJsonOutput).jsonArray

      val mgoResources = mutableListOf<MgoResource>()

      for (bundleJsonElement in bundleJsonArray) {
        val bundleJsonString = json.encodeToString(bundleJsonElement)

        // Get output of javascript call from getMgoResourceJson
        val getMgoResourceJsonOutput =
          jsEngineRepository.executeStringFunction(
            functionName = "getMgoResourceJson",
            parameters =
              listOf(
                bundleJsonString,
                fhirVersion.toJsonString(),
              ),
          )

        // Create mgo resource from output
        val referenceId =
          json
            .parseToJsonElement(getMgoResourceJsonOutput)
            .jsonObject["referenceId"]
            ?.jsonPrimitive
            ?.content ?: ""

        val profile =
          json
            .parseToJsonElement(getMgoResourceJsonOutput)
            .jsonObject["profile"]
            ?.jsonPrimitive
            ?.content ?: ""

        val mgoResource =
          MgoResource(
            referenceId = referenceId,
            profile = profile,
            json = getMgoResourceJsonOutput,
          )

        mgoResources.add(mgoResource)
      }

      // Return the list of created mgo resource objects
      return mgoResources
    }

    private fun FhirVersion.toJsonString(): String =
      json.encodeToString(
        mapOf(
          "fhirVersion" to this.toString(),
        ),
      )
  }
