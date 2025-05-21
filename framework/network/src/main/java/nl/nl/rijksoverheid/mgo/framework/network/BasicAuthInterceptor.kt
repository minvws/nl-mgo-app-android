package nl.nl.rijksoverheid.mgo.framework.network

import okhttp3.Credentials.basic
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

/**
 * An OkHttp interceptor that adds Basic Authentication headers to requests.
 *
 * @param user The username for authentication.
 * @param password The password for authentication.
 */
class BasicAuthInterceptor(user: String, password: String) : Interceptor {
  private val credentials = basic(user, password)

  @Throws(IOException::class)
  override fun intercept(chain: Interceptor.Chain): Response {
    val request: Request = chain.request()
    val authenticatedRequest =
      request.newBuilder()
        .header("Authorization", credentials).build()
    return chain.proceed(authenticatedRequest)
  }
}
