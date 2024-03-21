package nl.rijksoverheid.mgo.data.config

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Config(val id: Int)

val TEST_CONFIG = Config(id = 1)
