package nl.rijksoverheid.mgo.framework.test

import java.io.InputStream

/**
 * Loads a resource file from the test classpath.
 *
 * Intended for use in JVM tests where resources are located
 * in the test resources directory.
 */
fun getResource(fileName: String): InputStream {
  val stream =
    Thread
      .currentThread()
      .contextClassLoader
      ?.getResourceAsStream(fileName)

  return requireNotNull(stream) {
    "Resource not found: $fileName"
  }
}
