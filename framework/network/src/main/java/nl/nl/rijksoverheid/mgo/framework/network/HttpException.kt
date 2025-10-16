package nl.nl.rijksoverheid.mgo.framework.network

import java.io.IOException

data class HttpException(
  val code: Int,
) : IOException()
