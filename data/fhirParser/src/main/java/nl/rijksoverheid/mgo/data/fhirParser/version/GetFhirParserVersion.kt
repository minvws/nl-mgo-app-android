package nl.rijksoverheid.mgo.data.fhirParser.version

/**
 * Get the version of the FHIR Parser.
 */
interface GetFhirParserVersion {
  operator fun invoke(): String
}
