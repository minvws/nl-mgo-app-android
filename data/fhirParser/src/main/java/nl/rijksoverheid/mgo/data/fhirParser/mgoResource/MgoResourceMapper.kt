package nl.rijksoverheid.mgo.data.fhirParser.mgoResource

import nl.rijksoverheid.mgo.data.fhirParser.uiSchema.UiSchemaMapper

/**
 * Creates [MgoResource] based on a FHIR Response (https://hl7.org/fhir/).
 */
interface MgoResourceMapper {
  /**
   * Maps a fhir response to a a list of [MgoResource] that can then be used in [UiSchemaMapper].
   *
   * @param fhirBundleJson The fhir bundle json (https://www.hl7.org/fhir/bundle.html).
   * @param fhirVersion The [FhirVersion] of the [fhirBundleJson].
   * @return A list of [MgoResource].
   */
  suspend fun get(
    fhirBundleJson: String,
    fhirVersion: FhirVersion,
  ): List<MgoResource>
}
