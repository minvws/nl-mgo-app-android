package nl.rijksoverheid.mgo.framework.util.base64.file

import nl.rijksoverheid.mgo.framework.util.file.ReadLocalFile

class ReadLocalFileFromResources : ReadLocalFile {
  override fun invoke(fileName: String): String =
    this::class.java.classLoader
      ?.getResource(fileName)!!
      .readText(Charsets.UTF_8)
}
