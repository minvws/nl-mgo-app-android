package nl.rijksoverheid.mgo.feature.digid

/**
 * The view state for [DigidLoginScreen].
 *
 * @param loading If the authentication process is loading.
 */
internal data class DigidLoginScreenViewState(
  val loading: Boolean,
)
