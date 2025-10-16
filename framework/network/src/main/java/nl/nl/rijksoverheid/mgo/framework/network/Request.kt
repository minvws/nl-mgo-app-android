package nl.nl.rijksoverheid.mgo.framework.network

import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

fun OkHttpClient.executeRequest(request: Request): Result<Response> =
  try {
    val response = newCall(request).execute()
    if (response.isSuccessful) {
      Result.success(response)
    } else {
      val httpException = HttpException(code = response.code)
      Result.failure(httpException)
    }
  } catch (e: IOException) {
    Result.failure(e)
  }
