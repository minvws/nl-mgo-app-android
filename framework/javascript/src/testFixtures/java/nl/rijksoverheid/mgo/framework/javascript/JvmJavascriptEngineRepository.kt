package nl.rijksoverheid.mgo.framework.javascript

import android.annotation.SuppressLint
import com.whl.quickjs.wrapper.QuickJSContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import java.io.File

class JvmJavascriptEngineRepository(
  dispatcher: CoroutineDispatcher = newSingleThreadContext("QuickJsThread"),
) : JavascriptEngineRepository {
  private var quickJs: QuickJSContext? = null
  override var quickJsDispatcher: CoroutineDispatcher = dispatcher

  @SuppressLint("UnsafeDynamicallyLoadedCode")
  override suspend fun create() =
    withContext(quickJsDispatcher) {
      // Load the native library for jvm
      val nativeLibraryUrl = this::class.java.getResource("/libquickjs-java-wrapper.dylib")
      if (nativeLibraryUrl != null) {
        System.load(File(nativeLibraryUrl.toURI()).absolutePath)
      }

      // Create QuickJS
      val quickJs = QuickJSContext.create()
      quickJs.setMaxStackSize(16 * 1024 * 1024)

      // Make Quick JS statically available
      this@JvmJavascriptEngineRepository.quickJs = quickJs
    }

  override suspend fun load(fileName: String): Unit =
    withContext(quickJsDispatcher) {
      // Get javascript file as string
      val jsCode =
        this::class.java.classLoader
          ?.getResource(fileName)!!
          .readText(Charsets.UTF_8)

      // Load the javascript file into Quick JS
      val quickJs = get()
      quickJs.evaluate(jsCode)
    }

  override suspend fun get(): QuickJSContext = withContext(quickJsDispatcher) { requireNotNull(quickJs) { "QuickJS is not created" } }
}
