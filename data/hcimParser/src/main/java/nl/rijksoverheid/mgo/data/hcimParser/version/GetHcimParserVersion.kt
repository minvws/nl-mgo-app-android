package nl.rijksoverheid.mgo.data.hcimParser.version

interface GetHcimParserVersion {
  operator fun invoke(fileName: String = "mgo-fhir-data.iife.version.json"): String
}
