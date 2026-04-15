package nl.rijksoverheid.mgo.framework.test

import java.io.File

/**
 * Use to write a file created by Robolectric to the host machine so you can access it.
 */
fun File.writeToHost(): File {
  val file = File(System.getProperty("java.io.tmpdir"), name)
  file.writeBytes(readBytes())
  return file
}
