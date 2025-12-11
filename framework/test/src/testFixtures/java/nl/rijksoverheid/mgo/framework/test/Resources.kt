package nl.rijksoverheid.mgo.framework.test

fun readResourceFile(resourceName: String): String {
  val classLoader =
    Thread.currentThread().contextClassLoader
      ?: error("No classloader found for current thread")

  return classLoader
    .getResource(resourceName)
    ?.readText(Charsets.UTF_8)
    ?: error("Resource not found: $resourceName")
}
