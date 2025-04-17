package nl.rijksoverheid.mgo.data.fhirParser.mgoResource

/**
 * Represents FHIR version (https://hl7.org/fhir/).
 */
enum class FhirVersion(val versionNumber: String) {
  R3("3.0"),
  R4("4.0"),
}
