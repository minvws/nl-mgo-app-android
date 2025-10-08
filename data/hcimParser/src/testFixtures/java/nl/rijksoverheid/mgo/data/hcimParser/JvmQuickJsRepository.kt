package nl.rijksoverheid.mgo.data.hcimParser

import android.annotation.SuppressLint
import com.whl.quickjs.wrapper.QuickJSContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import nl.rijksoverheid.mgo.data.hcimParser.javascript.QuickJsRepository
import java.io.File

class JvmQuickJsRepository(
  dispatcher: CoroutineDispatcher = newSingleThreadContext("QuickJsThread"),
) : QuickJsRepository {
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

      // Load javascript file with functions that we share between clients
      val jsCode =
        this::class.java.classLoader
          ?.getResource("mgo-hcim-api.iife.js")!!
          .readText(Charsets.UTF_8)

      // Create QuickJS
      val quickJs = QuickJSContext.create()
      quickJs.setMaxStackSize(16 * 1024 * 1024)

      // Load the javascript file into Quick JS
      quickJs.evaluate(jsCode)

      // Make Quick JS statically available
      this@JvmQuickJsRepository.quickJs = quickJs
    }

  override suspend fun get(): QuickJSContext = withContext(quickJsDispatcher) { requireNotNull(quickJs) { "QuickJS is not created" } }
}
