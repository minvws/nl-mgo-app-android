package nl.rijksoverheid.mgo.framework.environment

/**
 * Represents the different environments the app can run in.
 *
 * @param versionCode The version code of the app.
 * @param deeplinkHost The host of the deeplink of this environment.
 */
sealed class Environment(open val versionCode: Int, open val deeplinkHost: String) {
  /**
   * Represents the demo environment of the app. This is a temporary environment that talks with the test server to demo some features.
   * @param versionCode The version code of the app.
   * @param deeplinkHost The host of the deeplink of this environment.
   */
  data class Demo(override val versionCode: Int, override val deeplinkHost: String) : Environment(versionCode, deeplinkHost)

  /**
   * Represents the test environment of the app. With this environment set, the app talks with the test server.
   * @param versionCode The version code of the app.
   * @param deeplinkHost The host of the deeplink of this environment.
   */
  data class Tst(override val versionCode: Int, override val deeplinkHost: String) : Environment(versionCode, deeplinkHost)

  /**
   * Represents the acceptance environment of the app. With this environment set, the app talks with the acceptance server.
   * @param versionCode The version code of the app.
   * @param deeplinkHost The host of the deeplink of this environment.
   */
  data class Acc(override val versionCode: Int, override val deeplinkHost: String) : Environment(versionCode, deeplinkHost)

  /**
   * Represents the production environment of the app. With this environment set, the app talks with the production server.
   * @param versionCode The version code of the app.
   * @param deeplinkHost The host of the deeplink of this environment.
   */
  data class Prod(override val versionCode: Int, override val deeplinkHost: String) : Environment(versionCode, deeplinkHost)

  /**
   * Represents a custom environment where you can point the server to any url.
   * @param versionCode The version code of the app.
   * @param deeplinkHost The host of the deeplink of this environment.
   * @param url The base url of the server to point to.
   */
  data class Custom(override val versionCode: Int, override val deeplinkHost: String, val url: String) :
    Environment(versionCode, deeplinkHost)
}
