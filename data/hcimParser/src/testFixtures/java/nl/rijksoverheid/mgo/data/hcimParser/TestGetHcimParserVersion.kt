package nl.rijksoverheid.mgo.data.hcimParser

import nl.rijksoverheid.mgo.data.hcimParser.version.GetHcimParserVersion

class TestGetHcimParserVersion : GetHcimParserVersion {
  override fun invoke(fileName: String): String =
    "{ \"version\": \"main\", \"git_ref\": \"d2c2081aefcaa7c0e8c413a1b8c654bcdcbe7705\", \"created\": \"2025-03-21T16:01:38\"}"
}
