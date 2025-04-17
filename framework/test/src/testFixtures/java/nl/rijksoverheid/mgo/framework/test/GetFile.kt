package nl.rijksoverheid.mgo.framework.test

import java.io.InputStream

/**
 * Helper method to load a json file from the resources folder.
 *
 * @param filePath The path of the json file.
 */
fun getJsonFromResources(filePath: String): String {
  val classLoader = object {}.javaClass.classLoader
  val `is`: InputStream = requireNotNull(classLoader?.getResourceAsStream(filePath))
  val size: Int = `is`.available()
  val buffer = ByteArray(size)
  `is`.read(buffer)
  `is`.close()
  return String(buffer)
}
