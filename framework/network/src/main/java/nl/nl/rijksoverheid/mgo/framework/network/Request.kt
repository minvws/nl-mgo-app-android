package nl.nl.rijksoverheid.mgo.framework.network

import retrofit2.HttpException
import java.io.IOException

/**
 * Executes a network request and wraps the result in a [Result] object.
 *
 * This function handles common network-related exceptions such as [IOException]
 * (for connectivity issues) and [HttpException] (for HTTP errors).
 *
 * @param block A lambda function representing the network request to execute.
 * @return A [Result] containing either the successful result or an error.
 */
inline fun <T : Any> executeNetworkRequest(block: () -> T): Result<T> {
  return try {
    // Execute the network request and wrap the successful result.
    Result.success(block())
  } catch (networkError: IOException) {
    // Handle network connectivity issues.
    Result.failure(networkError)
  } catch (httpError: HttpException) {
    // Handle HTTP errors (e.g., 4xx and 5xx responses).
    Result.failure(httpError)
  }
}
