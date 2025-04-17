package nl.rijksoverheid.mgo.data.fhirParser.version

class TestGetFhirParserVersion : GetFhirParserVersion {
  override fun invoke(): String {
    return "{ \"version\": \"main\", \"git_ref\": \"d2c2081aefcaa7c0e8c413a1b8c654bcdcbe7705\", \"created\": \"2025-03-21T16:01:38\"}"
  }
}
