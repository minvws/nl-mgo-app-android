package nl.rijksoverheid.mgo.feature.settings.about.opensource

/**
 * Represents an open source library used in the app.
 */
data class OpenSourceLibrary(
  val name: String,
  val description: String?,
  val website: String?,
)
