package nl.rijksoverheid.mgo.data.hcimParser.javascript

import android.content.Context
import com.whl.quickjs.android.QuickJSLoader
import com.whl.quickjs.wrapper.QuickJSContext
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExecutorCoroutineDispatcher
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.nio.charset.StandardCharsets
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
internal class AndroidQuickJsRepository
  @Inject
  constructor(
    @ApplicationContext private val context: Context,
  ) : QuickJsRepository {
    override val quickJsDispatcher: ExecutorCoroutineDispatcher = newSingleThreadContext("QuickJsThread")

    private var quickJs: QuickJSContext? = null

    override suspend fun create() =
      withContext(quickJsDispatcher) {
        // Init QuickJS for Android
        QuickJSLoader.init()

        // Load javascript file with functions that we share between clients
        val jsCode =
          context.assets
            .open("mgo-hcim-api.iife.js")
            .bufferedReader(StandardCharsets.UTF_8)
            .use { it.readText() }

        // Create Quick JS
        val quickJs = QuickJSContext.create()

        // Load the javascript file into Quick JS
        quickJs.evaluate(jsCode)

        // Make Quick JS statically available
        this@AndroidQuickJsRepository.quickJs = quickJs

        Timber.v("Quick JS created")
      }

    override suspend fun get(): QuickJSContext = withContext(quickJsDispatcher) { requireNotNull(quickJs) { "QuickJS is not created" } }
  }
