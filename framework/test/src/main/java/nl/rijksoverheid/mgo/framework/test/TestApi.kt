package nl.rijksoverheid.mgo.framework.test

import okhttp3.OkHttpClient
import java.io.InputStream

val TEST_OKHTTP_CLIENT =
    OkHttpClient
        .Builder()
        .build()

/**
 * Helper method to load json from the resources folder.
 * Useful for example unit tests where you want to load local json files into a mock web server.
 */
fun Class<*>.loadJsonFromResources(filePath: String): String {
    val `is`: InputStream = requireNotNull(classLoader?.getResourceAsStream(filePath))
    val size: Int = `is`.available()
    val buffer = ByteArray(size)
    `is`.read(buffer)
    `is`.close()
    return String(buffer)
}
