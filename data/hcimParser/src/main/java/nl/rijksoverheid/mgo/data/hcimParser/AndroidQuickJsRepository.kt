package nl.rijksoverheid.mgo.data.hcimParser

import android.content.Context
import com.whl.quickjs.android.QuickJSLoader
import com.whl.quickjs.wrapper.QuickJSContext
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AndroidQuickJsRepository
  @Inject
  constructor(
    private val context: Context,
  ) : QuickJsRepository {
    private var quickJs: QuickJSContext? = null

    override fun create() {
      // Init QuickJS for Android
      QuickJSLoader.init()

      // Load javascript file with functions that we share between clients
      val jsCode =
        context.assets
          .open("script.js")
          .bufferedReader(StandardCharsets.UTF_8)
          .use { it.readText() }

      // Create Quick JS
      val quickJs = QuickJSContext.create()

      // Load the javascript file into Quick JS
      quickJs.evaluate(jsCode)

      // Make Quick JS statically available
      this.quickJs = quickJs
    }

    override fun get(): QuickJSContext = requireNotNull(quickJs) { "QuickJS is not created" }
  }
