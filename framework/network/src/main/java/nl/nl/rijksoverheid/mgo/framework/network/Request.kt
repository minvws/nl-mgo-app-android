package nl.nl.rijksoverheid.mgo.framework.network

import retrofit2.HttpException
import java.io.IOException

inline fun <T : Any> executeNetworkRequest(block: () -> T): Result<T> {
    return try {
        Result.success(block())
    } catch (networkError: IOException) {
        Result.failure(networkError)
    } catch (httpError: HttpException) {
        Result.failure(httpError)
    }
}
