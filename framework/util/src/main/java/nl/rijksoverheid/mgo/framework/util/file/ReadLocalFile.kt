package nl.rijksoverheid.mgo.framework.util.file

interface ReadLocalFile {
  operator fun invoke(fileName: String): String
}
