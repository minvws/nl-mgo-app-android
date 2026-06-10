package nl.rijksoverheid.mgo.framework.javascript

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class ExecuteJavascriptTest {
  private val javascriptEngineRepository = JvmJavascriptEngineRepository()
  private val executeJavascript = ExecuteJavascript(javascriptEngineRepository)

  @Test
  fun testExecuteJavascript() =
    runTest {
      javascriptEngineRepository.create()
      javascriptEngineRepository.load("test.js")
      val output = executeJavascript.invoke("TestApi", "merge", listOf("Hello", "World"))
      assertEquals("Hello World", output)
    }
}
