package nl.rijksoverheid.mgo.framework.test

import okhttp3.OkHttpClient

val TEST_OKHTTP_CLIENT =
  OkHttpClient
    .Builder()
    .build()
