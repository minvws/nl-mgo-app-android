package nl.rijksoverheid.mgo.data.fhirParser.version

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import java.io.IOException
import javax.inject.Inject

/**
 * Get the version of the FHIR Parser.
 */
internal class DefaultGetFhirParserVersion
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
  ) : GetFhirParserVersion {
    /**
     * Return a json string that represents the version of the FHIR parser. This is not a user friendly string, example:
     * { "version": "main", "git_ref": "d2c2081aefcaa7c0e8c413a1b8c654bcdcbe7705", "created": "2025-03-21T16:01:38"}.
     *
     * @return The fhir parser version. Returns an empty string if the file containing the version cannot be read.
     */
    override fun invoke(): String {
      return try {
        context.assets.open(
          "mgo-fhir-data.iife.version.json",
        ).bufferedReader().use { it.readText() }
      } catch (e: IOException) {
        ""
      }
    }
  }
