package nl.rijksoverheid.mgo.framework.fhir

enum class FhirVersion(
  val stringValue: String,
) {
  R3("3.0"),
  R4("4.0"),
}
