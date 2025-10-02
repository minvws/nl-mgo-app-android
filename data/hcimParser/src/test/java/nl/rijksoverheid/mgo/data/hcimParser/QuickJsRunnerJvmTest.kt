package nl.rijksoverheid.mgo.data.hcimParser

import org.junit.Assert.assertTrue
import org.junit.Test
import java.io.File

class QuickJsRunnerJvmTest {
  @Test
  fun testCallGetBundleResourcesJson() {
    // 1. Haal het bestand uit resources
    val url = this::class.java.getResource("/libquickjs-java-wrapper.dylib")

    // 2. Laad de native library
    System.load(File(url.toURI()).absolutePath)

    val runner = QuickJsRunnerJvm()
    val result = runner.callGetBundleResourcesJson()

    println(result)
    assertTrue(result.isNotBlank())
    assertTrue(result.contains("resourceType"))
  }
}
